package br.com.sari_backend.services;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import br.com.sari_backend.models.TicketMeals;

public interface ITicketMealService {
  public List<TicketMeals> findAll();

  public TicketMeals save(TicketMeals meal, String email) throws NotFoundException;

  public TicketMeals update(String id, TicketMeals meal) throws NotFoundException;

  // public User getUserByEmail(String email) throws NotFoundException;

  // public void toggleUserActivation(String id, boolean mustBeActive) throws
  // NotFoundException;
}