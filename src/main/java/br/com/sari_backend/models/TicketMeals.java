package br.com.sari_backend.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.sari_backend.models.enums.DessertTypeEnum;
import br.com.sari_backend.models.enums.TicketMealStatusEnum;
import br.com.sari_backend.models.enums.TicketTypeEnum;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "ticket_meals")
public class TicketMeals extends ModelBase implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(nullable = false, unique = false)
  private User user;

  @Column(nullable = false)
  private String name;

  private String description;

  @Column(nullable = false)
  private DessertTypeEnum dessert;

  @Column(nullable = false)
  private TicketTypeEnum type;

  @JsonProperty("amount_tickets")
  @Column(name = "amount_tickets", nullable = false)
  private Integer amountTickets;

  @JsonProperty("available_tickets")
  @Column(name = "available_tickets", nullable = false)
  private Integer availableTickets;

  @Column(nullable = false)
  private TicketMealStatusEnum status;

  @JsonProperty("start_time")
  @Nullable
  @Column(name = "start_time", nullable = false)
  private LocalDateTime startTime;

  @JsonProperty("end_time")
  @Nullable
  @Column(name = "end_time", nullable = false)
  private LocalDateTime endTime;

  @OneToMany(mappedBy = "ticketMeal")
  private List<BookMeal> bookMeals;

  TicketMeals() {
    this.status = TicketMealStatusEnum.SCHEDULED;
    this.startTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 0));
    this.endTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(13, 30));
  }
}
