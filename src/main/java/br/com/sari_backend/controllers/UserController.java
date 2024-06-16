package br.com.sari_backend.controllers;

import java.util.List;
import java.util.stream.Collectors;

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
import br.com.sari_backend.dtos.user.UserDTO;
import br.com.sari_backend.models.User;
import br.com.sari_backend.models.enums.RoleEnum;
import br.com.sari_backend.services.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private IUserService userService;

  @PostMapping
  @RoleAnnotation(roles = { RoleEnum.ADM })
  public ResponseEntity<?> createUser(@RequestBody User data) {
    User user = userService.save(data);

    return new ResponseEntity<>(user, HttpStatus.CREATED);
  }

  @GetMapping
  @RoleAnnotation(roles = { RoleEnum.ADM })
  public ResponseEntity<?> listUsers() {
    List<User> users = userService.findAll();

    List<UserDTO> mappedUsers = users.stream().map((user) -> {
      UserDTO dto = new UserDTO();

      dto.setId(user.getId());
      dto.setName(user.getName());
      dto.setEmail(user.getEmail());
      dto.setPhone(user.getPhone());
      dto.setRole(user.getRole());
      dto.setActive(user.isActive());
      dto.setBlocked(user.isBlocked());

      return dto;
    }).collect(Collectors.toList());

    return new ResponseEntity<>(mappedUsers, HttpStatus.OK);
  }

  @PatchMapping("{id}/deactivate")
  @RoleAnnotation(roles = { RoleEnum.ADM })
  public ResponseEntity<?> disableUser(@PathVariable String id) {
    try {
      userService.toggleUserActivation(id, false);
      return new ResponseEntity<>("User deactivated!", HttpStatus.OK);

    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PatchMapping("{id}/activate")
  @RoleAnnotation(roles = { RoleEnum.ADM })
  public ResponseEntity<?> enableUser(@PathVariable String id) {
    try {
      userService.toggleUserActivation(id, true);
      return new ResponseEntity<>("User activated!", HttpStatus.OK);

    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
