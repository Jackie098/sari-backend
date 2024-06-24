package br.com.sari_backend.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import br.com.sari_backend.config.exceptions.ResourceNotFoundException;
import br.com.sari_backend.models.BookMeal;
import br.com.sari_backend.models.TicketMeals;
import br.com.sari_backend.models.User;
import br.com.sari_backend.models.embeddables.BookMealId;
import br.com.sari_backend.models.enums.BookMealStatusEnum;
import br.com.sari_backend.models.enums.TicketMealStatusEnum;
import br.com.sari_backend.repositories.BookMealRepository;

@Service
public class BookMealService implements IBookMealService {
  @Autowired
  private UserService userService;

  @Autowired
  private TicketMealService ticketMealService;

  @Autowired
  private BookMealRepository bookMealRepository;

  public List<BookMeal> findAll() {
    return bookMealRepository.findAll();
  };

  public List<BookMeal> findAllByUser(String email) throws ResourceNotFoundException {
    User user = userService.getUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));

    Optional<List<BookMeal>> optionalBooksByUser = bookMealRepository.findByUser(user);

    if (optionalBooksByUser.isEmpty()) {
      return List.of();
    }

    return optionalBooksByUser.get();
  };

  public BookMeal bookMeal(String mealId, String email) throws NotFoundException, BadRequestException {
    TicketMeals meal = ticketMealService.findById(UUID.fromString(mealId));

    LocalDateTime currentTime = LocalDateTime.now();
    boolean isScheduled = meal.getStatus().equals(TicketMealStatusEnum.SCHEDULED);

    if (!isScheduled) {
      System.out.println("Not is Scheduled");
      throw new BadRequestException();
    }

    if (!(currentTime.isAfter(meal.getStartTime().minusHours(2))
        && currentTime.isBefore(meal.getStartTime().minusHours(1)))) {
      System.out.println("time wrong");
      throw new NotFoundException();
    }

    if (meal.getAvailableTickets() == 0) {
      System.out.println("Available tickets is equal to 0");
      throw new BadRequestException();
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

  public BookMeal checkInStudent(String studentId) throws NotFoundException {
    Optional<BookMeal> bookMeal = bookMealRepository.findAvailableBookedByUserId(UUID.fromString(studentId));

    if (bookMeal.isEmpty()) {
      throw new NotFoundException();
    }

    bookMeal.get().setStatus(BookMealStatusEnum.USED);

    return bookMealRepository.save(bookMeal.get());
  }

  public void cancelBook(String mealId, String email) throws NotFoundException, BadRequestException {
    TicketMeals meal = ticketMealService.findById(UUID.fromString(mealId));

    LocalDateTime currentTime = LocalDateTime.now();
    boolean isScheduled = meal.getStatus().equals(TicketMealStatusEnum.SCHEDULED);

    if (!isScheduled) {
      System.out.println("Meal isn't scheduled - it's - " + meal.getStatus());
      throw new BadRequestException();
    }

    if (!(currentTime.isAfter(meal.getStartTime().minusHours(2))
        && currentTime.isBefore(meal.getStartTime().minusHours(1)))) {
      throw new NotFoundException();
    }

    User user = userService.getUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));

    BookMealId composedId = new BookMealId(user.getId(), meal.getId());

    Optional<BookMeal> optionalBookMeal = bookMealRepository.findById(composedId);

    if (optionalBookMeal.isEmpty()) {
      throw new NotFoundException();
    }

    meal.setAvailableTickets(meal.getAvailableTickets() + 1);

    BookMeal canceledMeal = optionalBookMeal.get();
    canceledMeal.setStatus(BookMealStatusEnum.CANCELED);

    bookMealRepository.save(canceledMeal);
  }
}
