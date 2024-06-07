package br.com.sari_backend.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sari_backend.models.TicketMeals;

public interface TicketMealRepository extends JpaRepository<TicketMeals, UUID> {
  // User findByEmail(String email);
}
