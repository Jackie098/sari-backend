package br.com.sari_backend.models.embeddables;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookMealId implements Serializable {
  @JsonProperty("user_id")
  private UUID userId;

  @JsonProperty("ticket_meal_id")
  private UUID ticketMealId;
}
