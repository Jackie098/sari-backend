package br.com.sari_backend.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.coyote.BadRequestException;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import br.com.sari_backend.dtos.ticketMeals.TicketMealUpdateDto;
import br.com.sari_backend.models.TicketMeals;
import br.com.sari_backend.models.User;
import br.com.sari_backend.models.enums.TicketMealStatusEnum;
import br.com.sari_backend.repositories.TicketMealRepository;

@Service
public class TicketMealService implements ITicketMealService {

  @Autowired
  private TicketMealRepository ticketMealRepository;

  @Autowired
  private UserService userService;

  public TicketMeals findById(UUID id) throws NotFoundException {
    Optional<TicketMeals> optionalMeal = ticketMealRepository.findById(id);

    if (!optionalMeal.isPresent()) {
      throw new NotFoundException();
    }

    return optionalMeal.get();
  }

  public List<TicketMeals> findAll() {
    return ticketMealRepository.findAll();
  };

  public TicketMeals save(TicketMeals meal, String email) throws NotFoundException {
    User user = userService.getUserByEmail(email);

    meal.setUser(user);

    return ticketMealRepository.save(meal);
  };

  public TicketMeals update(String id, TicketMealUpdateDto data) throws NotFoundException, BadRequestException {
    TicketMeals meal = findById(UUID.fromString(id));

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

    if (data.getStartTime() != null) {
      meal.setStartTime(data.getStartTime());
    }

    if (data.getEndTime() != null) {
      meal.setEndTime(data.getEndTime());
    }

    if (data.getStatus() != null) {
      LocalDateTime currentTime = LocalDateTime.now();

      boolean isScheduled = meal.getStatus().equals(TicketMealStatusEnum.SCHEDULED);
      boolean isAvailable = meal.getStatus().equals(TicketMealStatusEnum.AVAILABLE);
      boolean isBlocked = meal.getStatus().equals(TicketMealStatusEnum.BLOCKED);
      boolean isPaused = meal.getStatus().equals(TicketMealStatusEnum.PAUSED);
      boolean isFinished = meal.getStatus().equals(TicketMealStatusEnum.FINISHED);

      BadRequestException BadRequestException = new BadRequestException();

      switch (data.getStatus()) {
        case SCHEDULED:
          if (isScheduled || isBlocked || isFinished) {
            throw BadRequestException;
          }

          if (!currentTime.isBefore(meal.getStartTime().minusHours(1))) {
            throw BadRequestException;
          }

          break;

        case AVAILABLE:
          if (isBlocked || isFinished) {
            throw BadRequestException;
          }

          if (!(currentTime.isAfter(meal.getStartTime()) && currentTime.isBefore(meal.getEndTime()))) {
            throw BadRequestException;
          }

          break;

        case PAUSED:
          if (isPaused) {
            throw BadRequestException;
          }

          if (!(isScheduled || isAvailable)) {
            throw BadRequestException;
          }
          break;

        case BLOCKED:
          if (!meal.getStatus().equals(TicketMealStatusEnum.SCHEDULED)) {
            throw BadRequestException;
          }
          break;

        case FINISHED:
          if (isBlocked || isFinished) {
            throw BadRequestException;
          }

          if (!currentTime.isAfter(meal.getEndTime())) {
            throw BadRequestException;
          }
          break;

        default:
          throw new InternalException("None of use cases were covered!");
      }

      meal.setStatus(data.getStatus());
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