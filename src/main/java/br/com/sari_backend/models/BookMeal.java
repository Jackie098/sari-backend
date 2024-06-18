package br.com.sari_backend.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.sari_backend.models.embeddables.BookMealId;
import br.com.sari_backend.models.enums.BookMealStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "book_meal")
@Data
@ToString(callSuper = true, exclude = { "user", "ticketMeal" })
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BookMeal extends ModelBase implements Serializable {

  @EmbeddedId
  private BookMealId id;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private BookMealStatusEnum status;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("userId")
  @JoinColumn(name = "user_id")
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("ticketMealId")
  @JoinColumn(name = "ticket_meal_id")
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  private TicketMeals ticketMeal;

  public BookMeal(BookMealId id) {
    this.id = id;
    this.status = BookMealStatusEnum.BOOKED;
  }
}
