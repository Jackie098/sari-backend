package br.com.sari_backend;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.sari_backend.controllers.AuthController;
import br.com.sari_backend.controllers.BookMealController;
import br.com.sari_backend.controllers.TicketMealsController;
import br.com.sari_backend.controllers.UserController;

@SpringBootTest
public class SmokeTest {

  @Autowired
  private AuthController authController;
  @Autowired
  private UserController userController;
  @Autowired
  private TicketMealsController ticketMealController;
  @Autowired
  private BookMealController bookMealController;

  @Test
  void contextLoads() throws Exception {
    assertThat(authController).isNotNull();
    assertThat(userController).isNotNull();
    assertThat(ticketMealController).isNotNull();
    assertThat(bookMealController).isNotNull();
  }
}
