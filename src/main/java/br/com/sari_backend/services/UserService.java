package br.com.sari_backend.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import br.com.sari_backend.models.User;
import br.com.sari_backend.repositories.UserRepository;
import br.com.sari_backend.utils.AbstractPasswordUtils;

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
  public User save(User data) {
    String hashedPassword = passwordUtils.hashPass(data.getPassword());

    data.setPassword(hashedPassword);

    return userRepository.save(data);
  }

  @Override
  public User update(User user) {
    return userRepository.save(user);
  }

  @Override
  public User getUserByEmail(String email) throws NotFoundException {
    User user = userRepository.findByEmail(email);

    if (user == null) {
      throw new NotFoundException();
    }

    return user;
  };

  @Override
  public void toggleUserActivation(String id, boolean mustBeActive) throws NotFoundException {
    Optional<User> optionalUser = findById(UUID.fromString(id));

    if (!optionalUser.isPresent()) {
      throw new NotFoundException();
    }

    User user = optionalUser.get();
    user.setActive(mustBeActive);

    userRepository.save(user);
  }
}
