package br.com.sari_backend.dtos.ticketMeals;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.sari_backend.models.enums.DessertTypeEnum;
import br.com.sari_backend.models.enums.TicketTypeEnum;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTicketMealDTO {
  @NotNull
  private String name;

  @NotNull
  private String description;

  @NotNull
  private DessertTypeEnum dessert;

  @NotNull(message = "Dessert must be provided")
  private TicketTypeEnum type;

  @JsonProperty("amount_tickets")
  @NotNull
  private Integer amountTickets;

  @JsonProperty("available_tickets")
  @NotNull
  private Integer availableTickets;

  @JsonProperty("start_time")
  @Nullable
  @FutureOrPresent
  private LocalDateTime startTime;

  @JsonProperty("end_time")
  @Nullable
  @FutureOrPresent
  private LocalDateTime endTime;
}
