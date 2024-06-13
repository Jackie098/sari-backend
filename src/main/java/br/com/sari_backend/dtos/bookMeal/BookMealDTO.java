package br.com.sari_backend.dtos.bookMeal;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.sari_backend.models.embeddables.BookMealId;
import br.com.sari_backend.models.enums.BookMealStatusEnum;
import lombok.Data;

@Data
public class BookMealDTO {
  private BookMealId id;
  private BookMealStatusEnum status;

  @JsonProperty("reserved_at")
  private Instant reservedAt;
}
