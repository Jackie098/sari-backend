package br.com.sari_backend.utils;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import br.com.sari_backend.SariBackendApplicationTests;
import br.com.sari_backend.models.User;
import br.com.sari_backend.models.enums.RoleEnum;

public class TokenUtilsTests extends SariBackendApplicationTests {
  // FIXME: DI does not work here
  @Autowired
  private TokenUtils tokenUtils;

  @Value("${jwt.secret}")
  private String secret;

  @Test
  public void shouldGenerateAndDecodeToken() {
    // TokenUtils tokenUtils = new TokenUtils();
    SecretKey secretKey = tokenUtils.transformSecretToSecretKey(secret);

    User user = new User("Carlos Test", "1234", "89994138240", RoleEnum.ADM);
    Date expirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60);

    Map<String, String> token = tokenUtils.generateToken(user.getRole() + "-login", expirationDate, user, secret);

    assertTrue(tokenUtils.isAuthenticated(token.get("token"), secretKey));
  }

}
