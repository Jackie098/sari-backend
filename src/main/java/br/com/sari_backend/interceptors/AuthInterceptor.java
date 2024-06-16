package br.com.sari_backend.interceptors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import br.com.sari_backend.utils.AbstractTokenUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {
  @Value("${jwt.secret}")
  private String secret;

  @Autowired
  private AbstractTokenUtils tokenUtils;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    final String authHeader = request.getHeader("authorization");

    if (authHeader == null) {
      return false;
    }

    SecretKey secretKey = tokenUtils.transformSecretToSecretKey(secret);
    final String token = authHeader.substring("Bearer ".length());

    if (!tokenUtils.isAuthenticated(token, secretKey)) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("User doesn't authenticated or token doesn't valid anymore!");
      return false;
    }

    Jws<Claims> decodedToken = tokenUtils.decodeToken(token, secretKey);

    String userRole = tokenUtils.getRole(decodedToken);
    String userEmail = tokenUtils.getEmail(decodedToken);

    request.setAttribute("role", userRole);
    request.setAttribute("email", userEmail);

    return true;
  }
}
