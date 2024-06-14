package br.com.sari_backend.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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

  // FIXME: Add DTO to avoid circular dependency
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
      TicketMealDTO dto = new TicketMealDTO();

      dto.setId(meal.getId());
      dto.setName(meal.getName());
      dto.setAmountTickets(meal.getAmountTickets());
      dto.setAvailableTickets(meal.getAvailableTickets());
      dto.setDescription(meal.getDescription());
      dto.setDessert(meal.getDessert());
      dto.setStartTime(meal.getStartTime());
      dto.setEndTime(meal.getEndTime());
      dto.setStatus(meal.getStatus());
      dto.setType(meal.getType());

      return new ResponseEntity<>(dto, HttpStatus.OK);
    } catch (BadRequestException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/{id}")
  @RoleAnnotation(roles = { RoleEnum.ADM, RoleEnum.SERVIDOR })
  public ResponseEntity<?> deleteMeal(@PathVariable String id) {
    try {
      ticketService.delete(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
  }
}
