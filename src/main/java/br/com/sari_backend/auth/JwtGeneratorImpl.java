package br.com.sari_backend.auth;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.sari_backend.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;

@Service
public class JwtGeneratorImpl implements JwtGeneratorInterface {

  // @Value("${jwt.secret}")
  // private String secret;

  // TODO: Refactor this code
  // https://github.com/jwtk/jjwt/discussions/869
  @Override
  public Map<String, String> generateToken(User user) {
    String jwtToken = "";

    jwtToken = Jwts.builder()
        .subject(user.getEmail())
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
        .claims()
        .add("email", user.getEmail())
        .add("role", user.getRole())
        .add("isActive", user.isActive())
        .add("isBlocked", user.isBlocked())
        .and()
        .signWith(SIG.HS256.key().build(),
            Jwts.SIG.HS256)
        .compact();

    Map<String, String> jwtTokenGen = new HashMap<>();
    jwtTokenGen.put("token", jwtToken);

    return jwtTokenGen;
  }

}
