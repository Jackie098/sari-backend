package br.com.sari_backend.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthDTO {
  @Email(message = "Must be valid email")
  private String email;

  @NotEmpty(message = "Must not be empty")
  @Size(min = 4, max = 16, message = "Must be between 4 and 16 characters")
  private String password;
}
