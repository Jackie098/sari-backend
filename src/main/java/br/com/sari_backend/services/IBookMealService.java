package br.com.sari_backend.services;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import br.com.sari_backend.models.BookMeal;

public interface IBookMealService {
  public List<BookMeal> findAll();

  public List<BookMeal> findAllByUser(String email) throws NotFoundException;

  public BookMeal bookMeal(String mealId, String email) throws NotFoundException, BadRequestException;

  public BookMeal checkInStudent(String studentId) throws NotFoundException;

  public void cancelBook(String mealId, String email) throws NotFoundException;
}
