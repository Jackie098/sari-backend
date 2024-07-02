package br.com.sari_backend.controllers;

import java.util.List;

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
import br.com.sari_backend.dtos.ticketMeals.CreateTicketMealDTO;
import br.com.sari_backend.dtos.ticketMeals.TicketMealDTO;
import br.com.sari_backend.dtos.ticketMeals.UpdateTicketMealDto;
import br.com.sari_backend.mappers.GenericMapper;
import br.com.sari_backend.models.TicketMeals;
import br.com.sari_backend.models.enums.RoleEnum;
import br.com.sari_backend.services.contracts.ITicketMealService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/meal")
public class TicketMealsController {

  private GenericMapper mapper;

  @Autowired
  private ITicketMealService ticketService;

  TicketMealsController() {
    this.mapper = GenericMapper.getInstance();
  }

  @PostMapping
  @RoleAnnotation(roles = { RoleEnum.ADM, RoleEnum.SERVIDOR })
  public ResponseEntity<?> createMeal(@Valid @RequestBody CreateTicketMealDTO data, HttpServletRequest request) {
    String email = (String) request.getAttribute("email");

    TicketMeals convertedData = mapper.toObject(data, TicketMeals.class, true);
    TicketMeals meal = ticketService.save(convertedData, email);

    CreateTicketMealDTO dto = mapper.toObject(meal, CreateTicketMealDTO.class);

    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  @GetMapping
  @RoleAnnotation(roles = { RoleEnum.ADM, RoleEnum.SERVIDOR, RoleEnum.ALUNO })
  public ResponseEntity<?> listMeals() {
    List<TicketMeals> ticketMeals = ticketService.findAll();

    List<TicketMealDTO> mappedTicketMeals = mapper.toList(ticketMeals, TicketMealDTO.class);

    return new ResponseEntity<>(mappedTicketMeals, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  @RoleAnnotation(roles = { RoleEnum.ADM, RoleEnum.SERVIDOR })
  public ResponseEntity<?> updateMeal(@PathVariable String id, @Valid @RequestBody UpdateTicketMealDto data) {
    TicketMeals dataConverted = mapper.toObject(data, TicketMeals.class);
    TicketMeals meal = ticketService.update(id, dataConverted);

    UpdateTicketMealDto dto = mapper.toObject(meal, UpdateTicketMealDto.class);

    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  @RoleAnnotation(roles = { RoleEnum.ADM, RoleEnum.SERVIDOR })
  public ResponseEntity<?> deleteMeal(@PathVariable String id) {
    ticketService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
