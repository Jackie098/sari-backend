package br.com.sari_backend.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sari_backend.models.User;
import br.com.sari_backend.services.IAuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
  @Autowired
  private IAuthService authService;

  @PostMapping
  public ResponseEntity<?> login(@RequestBody User data) {
    try {
      Map<String, String> token = authService.login(data);

      return new ResponseEntity<>(token, HttpStatus.OK);

    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

}
