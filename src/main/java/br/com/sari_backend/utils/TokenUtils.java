package br.com.sari_backend.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.sari_backend.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class TokenUtils implements ITokenUtils {
  @Value("${jwt.secret}")
  private String secret;

  public Map<String, String> generateToken(String subject, Date expiration, User user) {
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

  public boolean isAuthenticated(HttpServletRequest request) {
    final String authHeader = request.getHeader("authorization");
    final boolean isBearerToken = authHeader.startsWith("Bearer ");

    if (authHeader == null || !isBearerToken) {
      return false;
    }

    final String token = authHeader.substring("Bearer ".length());
    final SecretKey secretKey = transformSecretToSecretKey(secret);

    try {
      decodeToken(token, secretKey);
    } catch (Exception e) {
      return false;
    }

    return true;
  }

  public SecretKey transformSecretToSecretKey(String secret) {
    return decodeToSecretKey(encodeSecret(secret));
  }

  public String encodeSecret(String secret) {
    var secretBytes = secret.getBytes();
    return Encoders.BASE64.encode(secretBytes);
  }

  public SecretKey decodeToSecretKey(String secret) {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
  }

  public Jws<Claims> decodeToken(String token, SecretKey secretKey) {
    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
  }
}
