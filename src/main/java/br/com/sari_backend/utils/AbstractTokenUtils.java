package br.com.sari_backend.utils;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import br.com.sari_backend.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public abstract class AbstractTokenUtils {
  public abstract Map<String, String> generateToken(String subject, Date expiration, User user, String secret);

  public abstract Jws<Claims> decodeToken(String token, SecretKey secretKey);

  public abstract String getRole(Jws<Claims> token);

  public abstract String getEmail(Jws<Claims> token);

  public abstract boolean isAuthenticated(String token, SecretKey secretKey);

  public abstract SecretKey transformSecretToSecretKey(String secret);
}
