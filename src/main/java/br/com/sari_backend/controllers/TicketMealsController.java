package br.com.sari_backend.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sari_backend.annotations.RoleAnnotation;
import br.com.sari_backend.dtos.ticketMeals.TicketMealDTO;
import br.com.sari_backend.dtos.ticketMeals.TicketMealUpdateDto;
import br.com.sari_backend.models.TicketMeals;
import br.com.sari_backend.models.enums.RoleEnum;
import br.com.sari_backend.services.ITicketMealService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/meal")
public class TicketMealsController {

  @Autowired
  private ITicketMealService ticketService;

  @PostMapping
  @RoleAnnotation(roles = { RoleEnum.ADM, RoleEnum.SERVIDOR })
  public ResponseEntity<?> createMeal(@RequestBody TicketMeals data, HttpServletRequest request) {
    try {
      String email = (String) request.getAttribute("email");

      TicketMeals meal = ticketService.save(data, email);
      return new ResponseEntity<>(meal, HttpStatus.OK);

    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping
  @RoleAnnotation(roles = { RoleEnum.ADM, RoleEnum.SERVIDOR, RoleEnum.ALUNO })
  public ResponseEntity<?> listMeals() {
    List<TicketMeals> ticketMeals = ticketService.findAll();

    List<TicketMealDTO> mappedTicketMeals = ticketMeals.stream().map((ticketMeal) -> {
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
    }).collect(Collectors.toList());

    return new ResponseEntity<>(mappedTicketMeals, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  @RoleAnnotation(roles = { RoleEnum.ADM, RoleEnum.SERVIDOR })
  public ResponseEntity<?> updateMeal(@PathVariable String id, @RequestBody TicketMealUpdateDto data) {
    try {
      TicketMeals meal = ticketService.update(id, data);
      return new ResponseEntity<>(meal, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  @RoleAnnotation(roles = { RoleEnum.ADM, RoleEnum.SERVIDOR })
  public ResponseEntity<?> deleteMeal(@PathVariable String id) {
    try {
      ticketService.delete(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
  }

}
