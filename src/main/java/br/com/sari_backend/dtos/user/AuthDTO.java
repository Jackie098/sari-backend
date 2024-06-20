package br.com.sari_backend.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthDTO {
  @Email
  private String email;

  @NotEmpty
  // TODO: Add customized validation
  private String password;
}
