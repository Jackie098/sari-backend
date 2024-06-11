package br.com.sari_backend.models.embeddables;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Embeddable;

@Embeddable
// @AllArgsConstructor
//
public class BookMealId implements Serializable {
  // @ManyToOne
  // @JoinColumn
  private UUID userId;

  // @ManyToOne
  // @JoinColumn(name = "ticket_meal_id")
  private UUID ticketMealId;

  // public BookMealId(User user, TicketMeals ticketMeal) {
  // this.user = user;
  // this.ticketMeal = ticketMeal;
  // }

}
