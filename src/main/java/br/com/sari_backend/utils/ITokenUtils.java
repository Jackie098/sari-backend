package br.com.sari_backend.utils;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import br.com.sari_backend.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;

public interface ITokenUtils {
  public Map<String, String> generateToken(String subject, Date expiration, User user);

  public boolean isAuthenticated(HttpServletRequest request);

  public String encodeSecret(String secret);

  public SecretKey decodeToSecretKey(String secret);

  public Jws<Claims> decodeToken(String token, SecretKey secretKey);
}
