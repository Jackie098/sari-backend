package br.com.sari_backend.models;

import java.io.Serializable;

import br.com.sari_backend.models.embeddables.BookMealId;
import br.com.sari_backend.models.enums.BookMealStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "book_meal")
@Data
@EqualsAndHashCode(callSuper = true)
public class BookMeal extends ModelBase implements Serializable {

  @EmbeddedId
  private BookMealId id;

  @Column(nullable = false)
  private BookMealStatusEnum status;

  @ManyToOne
  @MapsId("userId")
  private User user;

  @ManyToOne
  @MapsId("ticketMealId")
  private TicketMeals ticketMeal;

  public BookMeal(BookMealId id) {
    this.id = id;
    this.status = BookMealStatusEnum.BOOKED;
  }
}
