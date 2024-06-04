package br.com.sari_backend.services;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import br.com.sari_backend.models.User;

@Service
public interface UserService {
  public User save(User user);

  public User getUserByNameAndPassword(String name, String password) throws NotFoundException;
}