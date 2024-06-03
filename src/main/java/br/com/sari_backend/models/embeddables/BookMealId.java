package br.com.sari_backend.models.embeddables;

import java.io.Serializable;

import br.com.sari_backend.models.TicketMeals;
import br.com.sari_backend.models.User;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class BookMealId implements Serializable {
  @ManyToOne
  @JoinColumn
  private User user;

  @ManyToOne
  @JoinColumn(name = "ticket_meal_id")
  private TicketMeals ticketMeal;

}
