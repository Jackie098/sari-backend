package br.com.sari_backend.services;

import java.util.List;

import br.com.sari_backend.models.BookMeal;

public interface IBookMealService {
  public List<BookMeal> findAll();

  public List<BookMeal> findAllByUser(String email);

  public BookMeal bookMeal(String mealId, String email);

  public BookMeal checkInStudent(String studentId);

  public void cancelBook(String mealId, String email);
}
