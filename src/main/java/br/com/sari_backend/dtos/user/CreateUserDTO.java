package br.com.sari_backend.dtos.user;

import br.com.sari_backend.models.enums.RoleEnum;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
  @NotEmpty
  private String name;

  @NotEmpty
  private String password;

  @NotEmpty
  @Email
  private String email;

  @NotEmpty
  @Size(min = 11, max = 13)
  private String phone;

  @Nullable
  private RoleEnum role;
}
