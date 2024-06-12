package br.com.sari_backend.dtos.user;

import java.util.UUID;

import br.com.sari_backend.models.enums.RoleEnum;
import lombok.Data;

@Data
public class UserDTO {
  private UUID id;
  private String name;
  private String email;
  private String phone;
  private RoleEnum role;
  private boolean isActive;
  private boolean isBlocked;
}
