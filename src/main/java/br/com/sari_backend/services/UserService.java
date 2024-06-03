package br.com.sari_backend.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.sari_backend.models.User;
import br.com.sari_backend.repositories.UserRepository;

@Service
public class UserService {

  private UserRepository userRepository;

  public Optional<User> findById(UUID id) {
    return userRepository.findById(id);
  };

  public List<User> findAll() {
    return userRepository.findAll();
  };

  public User save(User user) {
    return userRepository.save(user);
  }

  public User update(User user) {
    return userRepository.save(user);
  };

}
