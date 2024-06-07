package br.com.sari_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sari_backend.annotations.RoleAnnotation;
import br.com.sari_backend.models.User;
import br.com.sari_backend.models.enums.RoleEnum;
import br.com.sari_backend.services.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private IUserService userService;

  @PostMapping
  @RoleAnnotation(roles = RoleEnum.ADM)
  public ResponseEntity<?> createUser(@RequestBody User data) {
    try {
      User user = userService.save(data);

      return new ResponseEntity<>(user, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping
  @RoleAnnotation(roles = { RoleEnum.ADM })
  public ResponseEntity<?> listUsers() {
    return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
  }
}
