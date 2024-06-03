package br.com.sari_backend.models;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import br.com.sari_backend.models.enums.DessertTypeEnum;
import br.com.sari_backend.models.enums.TicketMealStatusEnum;
import br.com.sari_backend.models.enums.TicketTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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

  @OneToOne
  @JoinColumn()
  private User user;

  private String description;

  @Column(nullable = false)
  private DessertTypeEnum dessert;

  @Column(nullable = false)
  private TicketTypeEnum type;

  @Column(name = "amount_tickets", nullable = false)
  private Integer amountTickets;

  @Column(name = "available_tickets", nullable = false)
  private Integer availableTickets;

  @Column(nullable = false)
  private TicketMealStatusEnum status;

  @Column(name = "start_time", nullable = false)
  private Date startTime;

  @Column(name = "end_time", nullable = false)
  private Date endTime;

  TicketMeals() {
    this.status = TicketMealStatusEnum.SCHEDULED;
  }
}
