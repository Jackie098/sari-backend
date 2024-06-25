package br.com.sari_backend.controllers;

import java.util.Map;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sari_backend.dtos.user.AuthDTO;
import br.com.sari_backend.mappers.GenericMapper;
import br.com.sari_backend.models.User;
import br.com.sari_backend.services.IAuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
  @Autowired
  private IAuthService authService;

  @PostMapping
  public ResponseEntity<?> login(@Valid @RequestBody AuthDTO data) throws BadRequestException, NotFoundException {
    GenericMapper mapper = GenericMapper.getInstance();

    User loginData = mapper.toObject(data, User.class);

    Map<String, String> token = authService.login(loginData);

    return new ResponseEntity<>(token, HttpStatus.OK);
  }

}
