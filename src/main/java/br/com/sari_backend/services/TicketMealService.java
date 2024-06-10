package br.com.sari_backend.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import br.com.sari_backend.dtos.ticketMeals.MealUpdateDto;
import br.com.sari_backend.models.TicketMeals;
import br.com.sari_backend.models.User;
import br.com.sari_backend.repositories.TicketMealRepository;

@Service
public class TicketMealService implements ITicketMealService {

  @Autowired
  private TicketMealRepository ticketMealRepository;

  @Autowired
  private UserService userService;

  public Optional<TicketMeals> findById(UUID id) {
    return ticketMealRepository.findById(id);
  }

  public List<TicketMeals> findAll() {
    return ticketMealRepository.findAll();
  };

  public TicketMeals save(TicketMeals meal, String email) throws NotFoundException {
    User user = userService.getUserByEmail(email);

    meal.setUser(user);

    return ticketMealRepository.save(meal);
  };

  public TicketMeals update(String id, MealUpdateDto data) throws NotFoundException {
    Optional<TicketMeals> optionalMeal = findById(UUID.fromString(id));

    if (!optionalMeal.isPresent()) {
      throw new NotFoundException();
    }

    TicketMeals meal = optionalMeal.get();

    // TODO: Use mapper? dto?
    if (data.getName() != null) {
      meal.setName(data.getName());
    }
    if (data.getDescription() != null) {
      meal.setDescription(data.getDescription());
    }
    if (data.getDessert() != null) {
      meal.setDessert(data.getDessert());
    }
    if (data.getType() != null) {
      meal.setType(data.getType());
    }
    if (data.getAmountTickets() != null) {
      meal.setAmountTickets(data.getAmountTickets());
    }
    if (data.getAvailableTickets() != null) {
      meal.setAvailableTickets(data.getAvailableTickets());
    }
    if (data.getStatus() != null) {
      meal.setStatus(data.getStatus());
    }
    if (data.getStartTime() != null) {
      meal.setStartTime(data.getStartTime());
    }
    if (data.getEndTime() != null) {
      meal.setEndTime(data.getEndTime());
    }

    return ticketMealRepository.save(meal);
  };

  public void delete(String id) throws NotFoundException {
    Optional<TicketMeals> ticketMeals = ticketMealRepository.findById(UUID.fromString(id));

    if (ticketMeals.isEmpty()) {
      throw new NotFoundException();
    }

    ticketMealRepository.deleteById(UUID.fromString(id));
  }
}