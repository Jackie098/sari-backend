package br.com.sari_backend.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sari_backend.config.exceptions.ResourceNotFoundException;
import br.com.sari_backend.config.exceptions.ResourcePersistenceException;
import br.com.sari_backend.models.User;
import br.com.sari_backend.repositories.UserRepository;
import br.com.sari_backend.utils.AbstractPasswordUtils;
import jakarta.persistence.EntityExistsException;

@Service
public class UserService implements IUserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AbstractPasswordUtils passwordUtils;

  @Override
  public Optional<User> findById(UUID id) {
    return userRepository.findById(id);
  };

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  };

  @Override
  public User save(User data) throws EntityExistsException {
    String hashedPassword = passwordUtils.hashPass(data.getPassword());
    Optional<User> user = userRepository.findByEmail(data.getEmail());

    if (user.isPresent()) {
      throw new ResourcePersistenceException("User already exists");
    }

    data.setPassword(hashedPassword);

    return userRepository.save(data);
  }

  @Override
  public User update(User data) {
    userRepository.findByEmail(data.getEmail())
        .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

    return userRepository.save(data);
  }

  @Override
  public Optional<User> getUserByEmail(String email) {
    Optional<User> user = userRepository.findByEmail(email);

    return user;
  };

  @Override
  public void toggleUserActivation(String id, boolean mustBeActive) {
    User user = findById(UUID.fromString(id))
        .orElseThrow(() -> new ResourceNotFoundException("User not founded!"));

    user.setActive(mustBeActive);

    userRepository.save(user);
  }
}
