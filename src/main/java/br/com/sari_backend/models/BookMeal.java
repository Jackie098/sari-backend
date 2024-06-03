package br.com.sari_backend.models;

import java.io.Serializable;

import br.com.sari_backend.models.embeddables.BookMealId;
import br.com.sari_backend.models.enums.BookMealStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "book_meal")
@Data
@EqualsAndHashCode(callSuper = true)
public class BookMeal extends ModelBase implements Serializable {
  // @Id
  // private User user;

  // @Id
  // private TicketMeals ticketMeals;
  @EmbeddedId
  private BookMealId id;

  // @ManyToOne
  // @JoinColumn
  // private User user;

  // @ManyToOne
  // @JoinColumn(name = "ticket_meal")
  // private TicketMeals ticketMeal;

  @Column(nullable = false)
  private BookMealStatusEnum status;
}
