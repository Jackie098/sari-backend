package br.com.sari_backend.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import br.com.sari_backend.models.TicketMeals;

public interface ITicketMealService {
  public List<TicketMeals> findAll();

  public Optional<TicketMeals> findById(UUID id);

  public TicketMeals save(TicketMeals meal, String email) throws NotFoundException;

  public TicketMeals update(String id, TicketMeals meal) throws NotFoundException;

  public void delete(String id) throws NotFoundException;
}