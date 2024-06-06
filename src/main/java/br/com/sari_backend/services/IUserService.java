package br.com.sari_backend.services;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import br.com.sari_backend.models.User;

public interface IUserService {
  public List<User> findAll();

  public User save(User user);

  public User getUserByEmail(String email) throws NotFoundException;
}