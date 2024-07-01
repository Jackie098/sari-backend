package br.com.sari_backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sari_backend.annotations.RoleAnnotation;
import br.com.sari_backend.dtos.user.CreateUserDTO;
import br.com.sari_backend.dtos.user.UserDTO;
import br.com.sari_backend.mappers.GenericMapper;
import br.com.sari_backend.models.User;
import br.com.sari_backend.models.enums.RoleEnum;
import br.com.sari_backend.openapi.UserControllerApi;
import br.com.sari_backend.services.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "User", description = "Operations related to user")
@RestController
@RequestMapping("/user")
public class UserController implements UserControllerApi {

  private GenericMapper mapper;

  @Autowired
  private IUserService userService;

  UserController() {
    this.mapper = GenericMapper.getInstance();
  }

  @PostMapping
  @RoleAnnotation(roles = { RoleEnum.ADM })
  public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO data) {
    User obj = mapper.toObject(data, User.class, true);

    User user = userService.save(obj);

    UserDTO dto = mapper.toObject(user, UserDTO.class);

    return new ResponseEntity<>(dto, HttpStatus.CREATED);
  }

  @GetMapping
  @RoleAnnotation(roles = { RoleEnum.ADM })
  public ResponseEntity<?> listUsers() {
    List<User> users = userService.findAll();

    List<UserDTO> mappedUsers = mapper.toList(users, UserDTO.class);

    return new ResponseEntity<>(mappedUsers, HttpStatus.OK);
  }

  @PatchMapping("{id}/deactivate")
  @RoleAnnotation(roles = { RoleEnum.ADM })
  public ResponseEntity<?> disableUser(@PathVariable String id) {
    userService.toggleUserActivation(id, false);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PatchMapping("{id}/activate")
  @RoleAnnotation(roles = { RoleEnum.ADM })
  public ResponseEntity<?> enableUser(@PathVariable String id) {
    userService.toggleUserActivation(id, true);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
