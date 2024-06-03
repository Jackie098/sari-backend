package br.com.sari_backend.services;

import org.springframework.stereotype.Service;

import lombok.Data;

@Service
@Data
public class AuthService {
  public String authenticate() {
    return "token";
  }
}
