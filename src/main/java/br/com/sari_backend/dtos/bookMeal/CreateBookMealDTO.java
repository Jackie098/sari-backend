package br.com.sari_backend.dtos.bookMeal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CreateBookMealDTO {
  @JsonProperty("meal_id")
  private String mealId;
}
