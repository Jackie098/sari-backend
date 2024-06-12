package br.com.sari_backend.dtos.bookMeal;

import br.com.sari_backend.models.embeddables.BookMealId;
import br.com.sari_backend.models.enums.BookMealStatusEnum;
import lombok.Data;

@Data
public class BookMealDTO {
  private BookMealId id;
  private BookMealStatusEnum status;
}
