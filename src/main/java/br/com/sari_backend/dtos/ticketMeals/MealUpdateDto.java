package br.com.sari_backend.dtos.ticketMeals;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.sari_backend.models.enums.DessertTypeEnum;
import br.com.sari_backend.models.enums.TicketMealStatusEnum;
import br.com.sari_backend.models.enums.TicketTypeEnum;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MealUpdateDto {
  @NotNull(message = "Name does not nullable")
  private String name;

  @Nullable
  private String description;

  @NotNull(message = "Dessert does not nullable")
  private DessertTypeEnum dessert;

  @NotNull(message = "Type does not nullable")
  private TicketTypeEnum type;

  @JsonProperty("amount_tickets")
  @NotNull(message = "Amount tickets does not nullable")
  private Integer amountTickets;

  @JsonProperty("available_tickets")
  @NotNull(message = "Available tickets does not nullable")
  private Integer availableTickets;

  @Nullable
  private TicketMealStatusEnum status;

  @JsonProperty("start_time")
  @Nullable
  private LocalDateTime startTime;

  @JsonProperty("end_time")
  @Nullable
  private LocalDateTime endTime;
}
