package br.com.sari_backend.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sari_backend.models.User;

public interface UserRepository extends JpaRepository<User, UUID> {
  User findByUsernameAndPassword(String username, String password);
}
