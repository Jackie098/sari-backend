package br.com.sari_backend.mappers;

import java.util.List;
import java.util.stream.Collectors;

import br.com.sari_backend.dtos.ticketMeals.TicketMealDTO;
import br.com.sari_backend.models.TicketMeals;

public class TicketMealsMapper {
  public List<TicketMealDTO> convertListToDto(List<TicketMeals> ticketMeals) {
    return ticketMeals.stream()
        .map(this::convertObjToDto)
        .collect(Collectors.toList());
  }

  private TicketMealDTO convertObjToDto(TicketMeals ticketMeal) {
    TicketMealDTO dto = new TicketMealDTO();
    dto.setId(ticketMeal.getId());
    dto.setName(ticketMeal.getName());
    dto.setDescription(ticketMeal.getDescription());
    dto.setDessert(ticketMeal.getDessert());
    dto.setType(ticketMeal.getType());
    dto.setAmountTickets(ticketMeal.getAmountTickets());
    dto.setAvailableTickets(ticketMeal.getAvailableTickets());
    dto.setStatus(ticketMeal.getStatus());
    dto.setStartTime(ticketMeal.getStartTime());
    dto.setEndTime(ticketMeal.getEndTime());

    return dto;
  }
}
