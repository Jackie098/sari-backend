package br.com.sari_backend.interceptors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import br.com.sari_backend.utils.TokenUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {
  @Value("${jwt.secret}")
  private String secret;

  @Autowired
  private TokenUtils tokenUtils;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    final String authHeader = request.getHeader("authorization");

    if (!tokenUtils.isAuthenticated(request)) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("User doesn't authenticated or token doesn't valid anymore!");
      return false;
    }

    String encodedSecret = tokenUtils.encodeSecret(secret);
    SecretKey secretKey = tokenUtils.decodeToSecretKey(encodedSecret);

    final String token = authHeader.substring("Bearer ".length());

    Jws<Claims> decodedToken = tokenUtils.decodeToken(token, secretKey);
    String userRole = decodedToken.getPayload().get("role", String.class);

    // System.out.println("role -> " + userRole);
    request.setAttribute("role", userRole);
    // System.out.println("get attri -> " + request.getAttribute("role"));

    return true;
    // return HandlerInterceptor.super.preHandle(request, response, handler);
  }

  // public boolean isAuthenticated(HttpServletRequest request) {
  // final String authHeader = request.getHeader("authorization");

  // if (authHeader == null || !authHeader.startsWith("Bearer ")) {
  // return false;
  // }

  // final SecretKey decodedSecret = transformToSecretKey(secretBase64);
  // final String token = authHeader.substring("Bearer ".length());

  // try {
  // decodeToken(token, decodedSecret);
  // } catch (Exception e) {
  // System.out.println("AuthInterceptor - isAuthenticated - e: " + e);
  // return false;
  // }

  // return true;
  // }

  // private SecretKey transformToSecretKey(String secret) {
  // return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
  // }

  // private Jws<Claims> decodeToken(String token, SecretKey decodedSecret) {
  // return
  // Jwts.parser().verifyWith(decodedSecret).build().parseSignedClaims(token);
  // }
}
