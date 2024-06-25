package br.com.sari_backend.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import br.com.sari_backend.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;

@Component
public class TokenUtils extends AbstractTokenUtils {
  public Map<String, String> generateToken(String subject, Date expiration, User user, String secret)
      throws WeakKeyException, IllegalArgumentException {
    SecretKey secretKey = transformSecretToSecretKey(secret);

    String buildedToken = Jwts.builder()
        .subject(subject)
        .issuedAt(new Date())
        .expiration(expiration)
        .claims()
        .add("email", user.getEmail())
        .add("role", user.getRole())
        .add("isActive", user.isActive())
        .add("isBlocked", user.isBlocked())
        .and()
        .signWith(secretKey)
        .compact();

    Map<String, String> token = new HashMap<>();
    token.put("token", buildedToken);

    return token;
  }

  public Jws<Claims> decodeToken(String token, SecretKey secretKey) {
    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
  }

  public String getRole(Jws<Claims> token) {
    return token.getPayload().get("role", String.class);
  }

  public String getEmail(Jws<Claims> token) {
    return token.getPayload().get("email", String.class);
  }

  public boolean isAuthenticated(String token, SecretKey secretKey) {
    try {
      decodeToken(token, secretKey);
    } catch (Exception e) {
      return false;
    }

    return true;
  }

  public SecretKey decodeSecretKey(String secret) {
    return Keys.hmacShaKeyFor(secret.getBytes());
  }

  public SecretKey transformSecretToSecretKey(String secret) {
    return Keys.hmacShaKeyFor(secret.getBytes());
  }
}
