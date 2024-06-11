package br.com.sari_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sari_backend.models.BookMeal;
import br.com.sari_backend.models.embeddables.BookMealId;

public interface BookMealRepository extends JpaRepository<BookMeal, BookMealId> {

}
