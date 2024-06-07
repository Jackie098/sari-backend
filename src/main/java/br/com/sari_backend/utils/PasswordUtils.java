package br.com.sari_backend.utils;

import org.springframework.stereotype.Component;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Component
public final class PasswordUtils extends AbstractPasswordUtils {

  public String hashPass(String password) {
    return BCrypt.withDefaults().hashToString(12, password.toCharArray());
  };

  public boolean checkPass(String password, String hashedPassword) {
    BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
    return result.verified;
  };
}
