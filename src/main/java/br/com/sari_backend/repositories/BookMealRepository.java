package br.com.sari_backend.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.sari_backend.models.BookMeal;
import br.com.sari_backend.models.User;
import br.com.sari_backend.models.embeddables.BookMealId;

// NOTE: Derived Query Methods
// https://www.baeldung.com/spring-data-derived-queries
public interface BookMealRepository extends JpaRepository<BookMeal, BookMealId> {
  Optional<List<BookMeal>> findByUser(User user);

  // JPQL - Java Persistence Query Language
  @Query("SELECT bm FROM BookMeal bm JOIN bm.ticketMeal tm WHERE bm.user.id = :studentId AND bm.user.isBlocked = false AND bm.user.isActive = true AND bm.status = 'BOOKED' AND tm.status = 'SCHEDULED'")
  Optional<BookMeal> findAvailableBookedByUserId(UUID studentId);
}
