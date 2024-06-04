package br.com.sari_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sari_backend.auth.JwtGeneratorInterface;
import br.com.sari_backend.models.User;
import br.com.sari_backend.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
  @Autowired
  private JwtGeneratorInterface jwtGenerator;

  @Autowired
  private UserService userService;

  @PostMapping
  public ResponseEntity<?> login(@RequestBody User user) {
    try {
      User userData = userService.getUserByNameAndPassword(user.getUsername(), user.getPassword());

      return new ResponseEntity<>(jwtGenerator.generateToken(userData), HttpStatus.OK);

    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

}
