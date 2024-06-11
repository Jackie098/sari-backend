package br.com.sari_backend.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import br.com.sari_backend.models.BookMeal;
import br.com.sari_backend.models.TicketMeals;
import br.com.sari_backend.models.User;
import br.com.sari_backend.models.embeddables.BookMealId;
import br.com.sari_backend.models.enums.BookMealStatusEnum;
import br.com.sari_backend.repositories.BookMealRepository;

@Service
public class BookMealService implements IBookMealService {
  @Autowired
  private UserService userService;

  @Autowired
  private TicketMealService ticketMealService;

  @Autowired
  private BookMealRepository bookMealRepository;

  public List<BookMeal> findAllByUser() {
    throw new UnsupportedOperationException("Method does not implemented yet");
  };

  public BookMeal bookMeal(String mealId, String email) throws NotFoundException {
    System.out.println("mealId - " + mealId);

    User user = userService.getUserByEmail(email);
    TicketMeals meal = ticketMealService.findById(UUID.fromString(mealId));

    BookMealId composedId = new BookMealId(user, meal);
    BookMeal newBook = new BookMeal(composedId);

    return bookMealRepository.save(newBook);

  };

  public void cancelBook(String mealId, String email) throws NotFoundException {
    User user = userService.getUserByEmail(email);
    TicketMeals meal = ticketMealService.findById(UUID.fromString(mealId));

    BookMealId composedId = new BookMealId(user, meal);

    Optional<BookMeal> optionalBookMeal = bookMealRepository.findById(composedId);

    if (optionalBookMeal.isEmpty()) {
      throw new NotFoundException();
    }

    BookMeal canceledMeal = optionalBookMeal.get();
    canceledMeal.setStatus(BookMealStatusEnum.CANCELED);
  }
}
