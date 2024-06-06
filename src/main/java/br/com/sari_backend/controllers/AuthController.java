package br.com.sari_backend.controllers;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sari_backend.models.User;
import br.com.sari_backend.services.IUserService;
import br.com.sari_backend.utils.ITokenUtils;

@RestController
@RequestMapping("/auth")
public class AuthController {
  @Autowired
  private ITokenUtils tokenUtils;

  @Autowired
  private IUserService userService;

  @PostMapping
  public ResponseEntity<?> login(@RequestBody User user) {
    try {
      User userData = userService.getUserByEmail(user.getEmail());
      Date expirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 10);

      System.out.println("AuthController - login - user.getName() - " + user.getName());
      System.out.println("AuthController - login - user.toString() - " + user.toString());

      Map<String, String> token = tokenUtils.generateToken(user.getRole() + "-login", expirationDate, userData);

      return new ResponseEntity<>(token, HttpStatus.OK);

    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

}
