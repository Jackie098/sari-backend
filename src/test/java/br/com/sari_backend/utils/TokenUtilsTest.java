package br.com.sari_backend.utils;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.sari_backend.models.User;
import br.com.sari_backend.models.enums.RoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { TokenUtils.class })
public class TokenUtilsTest {
  @Autowired
  private TokenUtils tokenUtils;

  // FIXME: DI from jwt.secret
  // @Value("${jwt.secret}")
  // private String secret;

  @Test
  @DisplayName("Should generate valid token and contain crucial values")
  public void shouldGenerateAndDecodeToken() {
    String secret = "my-secret-base-64-my-secret-base-64-~my-secret-base-64-my-secret-base-64-";
    SecretKey secretKey = tokenUtils.transformSecretToSecretKey(secret);

    User user = new User("Carlos Test", "carlos@gmail.com", "1234", "89994138240", RoleEnum.ADM);
    Date expirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60);

    Map<String, String> token = tokenUtils.generateToken(user.getRole() + "-login", expirationDate, user, secret);

    assertTrue(tokenUtils.isAuthenticated(token.get("token"), secretKey));

    Jws<Claims> decodedToken = tokenUtils.decodeToken(token.get("token"), secretKey);

    assertTrue(tokenUtils.getRole(decodedToken).equals(RoleEnum.ADM.toString()));
    assertTrue(tokenUtils.getEmail(decodedToken).equals(user.getEmail()));
  }

}
