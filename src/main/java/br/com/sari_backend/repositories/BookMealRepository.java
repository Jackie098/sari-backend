package br.com.sari_backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sari_backend.models.BookMeal;
import br.com.sari_backend.models.User;
import br.com.sari_backend.models.embeddables.BookMealId;

public interface BookMealRepository extends JpaRepository<BookMeal, BookMealId> {
  Optional<List<BookMeal>> findByUser(User user);
}
