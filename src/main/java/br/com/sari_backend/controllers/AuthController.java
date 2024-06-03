package br.com.sari_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import br.com.sari_backend.services.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AuthController {
  @Autowired
  private AuthService authService;

  @PostMapping("auth")
  public String auth(@RequestBody String entity) {

    return authService.authenticate();
  }

}
