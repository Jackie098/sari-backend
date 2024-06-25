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

import br.com.sari_backend.config.exceptions.BusinessException;
import br.com.sari_backend.config.exceptions.ResourceNotFoundException;
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

  public TicketMeals findById(UUID id) {
    return ticketMealRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("TicketMeal does not exists!"));
  }

  public List<TicketMeals> findAll() {
    return ticketMealRepository.findAll();
  };

  public TicketMeals save(TicketMeals meal, String email) {
    User user = userService.getUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));

    meal.setUser(user);

    return ticketMealRepository.save(meal);
  };

  public TicketMeals update(String id, TicketMeals data) {
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

      switch (data.getStatus()) {
        case SCHEDULED:
          if (isScheduled || isBlocked || isFinished) {
            throw new BusinessException("This meal does not update because it is: " + meal.getStatus());
          }

          if (!currentTime.isBefore(meal.getStartTime().minusHours(1))) {
            throw new BusinessException("This meal could be SCHEDULED by: " + meal.getStartTime().minusHours(1));
          }

          break;

        case AVAILABLE:
          if (isBlocked || isFinished) {
            throw new BusinessException("This meal does not update because it is: " + meal.getStatus());
          }

          if (!(currentTime.isAfter(meal.getStartTime()) && currentTime.isBefore(meal.getEndTime()))) {
            throw new BusinessException("This meal could not be AVAILABLE because it's out of time lapse: "
                + meal.getStartTime() + " - " + meal.getEndTime());
          }

          break;

        case PAUSED:
          if (isPaused) {
            throw new BusinessException("This meal does not update because it already: " + meal.getStatus());
          }

          if (!(isScheduled || isAvailable)) {
            throw new BusinessException(
                "This meal must be SCHEDULED or AVAILABLE to pause it. Actually it is: " + meal.getStatus());
          }

          break;

        case BLOCKED:
          if (!meal.getStatus().equals(TicketMealStatusEnum.SCHEDULED)) {
            throw new BusinessException(
                "This meal must be SCHEDULED to block it. Actually it is: " + meal.getStatus()
                    + ". Try pause it if possible.");
          }

          break;

        case FINISHED:
          if (isBlocked || isFinished) {
            throw new BusinessException("This meal does not update because it is: " + meal.getStatus());
          }

          if (!currentTime.isAfter(meal.getEndTime())) {
            throw new BusinessException(
                "This meal cannot be FINISHED yet. It will be available to change the status from: "
                    + meal.getEndTime());
          }
          break;

        default:
          throw new InternalException("None of use cases were covered!");
      }

      meal.setStatus(data.getStatus());
    }

    return ticketMealRepository.save(meal);
  };

  public void delete(String id) {
    ticketMealRepository.findById(UUID.fromString(id))
        .orElseThrow(() -> new ResourceNotFoundException("Ticket meal does not exist"));

    ticketMealRepository.deleteById(UUID.fromString(id));
  }
}