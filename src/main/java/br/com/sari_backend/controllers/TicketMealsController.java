package br.com.sari_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sari_backend.annotations.RoleAnnotation;
import br.com.sari_backend.models.TicketMeals;
import br.com.sari_backend.models.enums.RoleEnum;
import br.com.sari_backend.services.ITicketMealService;

@RestController
@RequestMapping("/meal")
public class TicketMealsController {

  @Autowired
  private ITicketMealService ticketService;

  @PostMapping
  @RoleAnnotation(roles = { RoleEnum.ADM })
  public ResponseEntity<?> createMeal(@RequestBody TicketMeals data) {
    try {
      TicketMeals meal = ticketService.save(data);
      return new ResponseEntity<>(meal, HttpStatus.OK);

    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping
  @RoleAnnotation(roles = { RoleEnum.ADM })
  public ResponseEntity<?> listMeals() {
    return new ResponseEntity<>(ticketService.findAll(), HttpStatus.OK);
  }

}
