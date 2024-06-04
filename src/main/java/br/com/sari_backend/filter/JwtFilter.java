package br.com.sari_backend.filter;

import java.io.IOException;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends GenericFilterBean {

  @Autowired
  private Environment env;
  // @Value("${jwt.secret}")
  // private String secret;

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    final HttpServletRequest request = (HttpServletRequest) servletRequest;
    final HttpServletResponse response = (HttpServletResponse) servletResponse;
    final String authHeader = request.getHeader("authorization");

    System.out.println("authHeader -> " + authHeader);
    var decodedSecret = Keys
        .hmacShaKeyFor(Decoders.BASE64.decode(env.getProperty("jwt.secret");));

    if ("OPTIONS".equals(request.getMethod())) {
      response.setStatus(HttpServletResponse.SC_OK);
      filterChain.doFilter(request, response);

    } else {
      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        throw new ServletException("Token does not provided!");
      }
    }

    // CHECKPOINT
    final String token = authHeader.substring(7);
    Jws<Claims> claims = Jwts.parser().verifyWith(decodedSecret).build().parseSignedClaims(token);
    // .parseClaimsJws(token).getBody();

    request.setAttribute("claims", claims);
    // request.setAttribute(, token);

    filterChain.doFilter(request, response);
  }

}
