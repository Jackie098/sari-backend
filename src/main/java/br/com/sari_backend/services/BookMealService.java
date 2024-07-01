package br.com.sari_backend.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sari_backend.config.exceptions.BusinessException;
import br.com.sari_backend.config.exceptions.ResourceNotFoundException;
import br.com.sari_backend.models.BookMeal;
import br.com.sari_backend.models.TicketMeals;
import br.com.sari_backend.models.User;
import br.com.sari_backend.models.embeddables.BookMealId;
import br.com.sari_backend.models.enums.BookMealStatusEnum;
import br.com.sari_backend.models.enums.TicketMealStatusEnum;
import br.com.sari_backend.repositories.BookMealRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookMealService implements IBookMealService {
  // @Autowired
  // NOTE: Alternative form to DI
  // add @RequiredArgsConstructor
  // add "final" in DI
  private final UserService userService;

  @Autowired
  private TicketMealService ticketMealService;

  @Autowired
  private BookMealRepository bookMealRepository;

  public List<BookMeal> findAll() {
    return bookMealRepository.findAll();
  };

  public List<BookMeal> findAllByUser(String email) {
    User user = userService.getUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));

    Optional<List<BookMeal>> optionalBooksByUser = bookMealRepository.findByUser(user);

    if (optionalBooksByUser.isEmpty()) {
      return List.of();
    }

    return optionalBooksByUser.get();
  };

  public BookMeal bookMeal(String mealId, String email) {
    TicketMeals meal = ticketMealService.findById(UUID.fromString(mealId));

    LocalDateTime currentTime = LocalDateTime.now();
    boolean isScheduled = meal.getStatus().equals(TicketMealStatusEnum.SCHEDULED);

    if (!isScheduled) {
      throw new BusinessException("Ticket meal is not available to SCHEDULE anymore.");
    }

    if (!(currentTime.isAfter(meal.getStartTime().minusHours(2))
        && currentTime.isBefore(meal.getStartTime().minusHours(1)))) {
      throw new BusinessException(
          "It's only possible to book meals between: " + meal.getStartTime() + " - " + meal.getEndTime());
    }

    if (meal.getAvailableTickets() == 0) {
      throw new BusinessException("There are no more tickets available for this meal.");
    }

    meal.setAvailableTickets(meal.getAvailableTickets() - 1);

    ticketMealService.save(meal, email);

    User user = userService.getUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));

    BookMealId composedId = new BookMealId(user.getId(), meal.getId());
    BookMeal newBook = new BookMeal(composedId);

    newBook.setUser(user);
    newBook.setTicketMeal(meal);

    return bookMealRepository.save(newBook);
  };

  public BookMeal checkInStudent(String studentId) {
    BookMeal bookMeal = bookMealRepository.findAvailableBookedByUserId(UUID.fromString(studentId))
        .orElseThrow(() -> new ResourceNotFoundException("Book for this user not founded"));

    bookMeal.setStatus(BookMealStatusEnum.USED);

    return bookMealRepository.save(bookMeal);
  }

  public void cancelBook(String mealId, String email) {
    TicketMeals meal = ticketMealService.findById(UUID.fromString(mealId));

    LocalDateTime currentTime = LocalDateTime.now();
    boolean isScheduled = meal.getStatus().equals(TicketMealStatusEnum.SCHEDULED);

    if (!isScheduled) {
      throw new BusinessException("Ticket meal is not available to CANCEL SCHEDULE anymore.");
    }

    if (!(currentTime.isAfter(meal.getStartTime().minusHours(2))
        && currentTime.isBefore(meal.getStartTime().minusHours(1)))) {
      throw new BusinessException(
          "It's only possible to cancel a book meal between: " + meal.getStartTime() + " - " + meal.getEndTime());
    }

    User user = userService.getUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));

    BookMealId composedId = new BookMealId(user.getId(), meal.getId());

    BookMeal bookMealToCancel = bookMealRepository.findById(composedId)
        .orElseThrow(() -> new ResourceNotFoundException("book meal not found"));

    meal.setAvailableTickets(meal.getAvailableTickets() + 1);

    bookMealToCancel.setStatus(BookMealStatusEnum.CANCELED);

    bookMealRepository.save(bookMealToCancel);
  }
}
