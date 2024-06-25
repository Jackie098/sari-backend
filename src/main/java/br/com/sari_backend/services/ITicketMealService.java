package br.com.sari_backend.services;

import java.util.List;
import java.util.UUID;

import br.com.sari_backend.models.TicketMeals;

public interface ITicketMealService {
  public List<TicketMeals> findAll();

  public TicketMeals findById(UUID id);

  public TicketMeals save(TicketMeals meal, String email);

  public TicketMeals update(String id, TicketMeals meal);

  public void delete(String id);
}