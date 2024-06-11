package br.com.sari_backend.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import br.com.sari_backend.dtos.ticketMeals.MealUpdateDto;
import br.com.sari_backend.models.TicketMeals;

public interface ITicketMealService {
  public List<TicketMeals> findAll();

  public TicketMeals findById(UUID id) throws NotFoundException;

  public TicketMeals save(TicketMeals meal, String email) throws NotFoundException;

  public TicketMeals update(String id, MealUpdateDto meal) throws NotFoundException;

  public void delete(String id) throws NotFoundException;
}