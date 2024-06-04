package br.com.sari_backend.auth;

import java.util.Map;

import br.com.sari_backend.models.User;

public interface JwtGeneratorInterface {
  Map<String, String> generateToken(User user);
}
