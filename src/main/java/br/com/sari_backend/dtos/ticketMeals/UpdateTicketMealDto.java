package br.com.sari_backend.dtos.ticketMeals;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import br.com.sari_backend.models.enums.DessertTypeEnum;
import br.com.sari_backend.models.enums.TicketMealStatusEnum;
import br.com.sari_backend.models.enums.TicketTypeEnum;
import jakarta.annotation.Nullable;
import lombok.Data;

// NOTE: Changed sobre attrs @JsonProperty to @JsonNaming snakeCase strategy to test and improve code
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UpdateTicketMealDto {
  @Nullable
  private String name;

  @Nullable
  private String description;

  @Nullable
  private DessertTypeEnum dessert;

  @Nullable
  private TicketTypeEnum type;

  // @JsonProperty("amount_tickets")
  @Nullable
  private Integer amountTickets;

  // @JsonProperty("available_tickets")
  @Nullable
  private Integer availableTickets;

  @Nullable
  private TicketMealStatusEnum status;

  // @JsonProperty("start_time")
  @Nullable
  private LocalDateTime startTime;

  // @JsonProperty("end_time")
  @Nullable
  private LocalDateTime endTime;
}
