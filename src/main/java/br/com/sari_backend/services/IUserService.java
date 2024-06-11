package br.com.sari_backend.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import br.com.sari_backend.models.User;
import jakarta.persistence.EntityExistsException;

public interface IUserService {
  public List<User> findAll();

  public Optional<User> findById(UUID id);

  public User getUserByEmail(String email) throws NotFoundException;

  public User save(User user) throws EntityExistsException;

  public User update(User user);

  public void toggleUserActivation(String id, boolean mustBeActive) throws NotFoundException;
}