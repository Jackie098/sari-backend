package br.com.sari_backend.controllers;

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
  @RoleAnnotation(roles = { RoleEnum.ADM })
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
  @RoleAnnotation(roles = { RoleEnum.ADM })
  public ResponseEntity<?> listMeals() {
    return new ResponseEntity<>(ticketService.findAll(), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  @RoleAnnotation(roles = { RoleEnum.ADM })
  public ResponseEntity<?> updateMeal(@PathVariable String id, @RequestBody TicketMeals data) {
    try {
      TicketMeals meal = ticketService.update(id, data);
      return new ResponseEntity<>(meal, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  @RoleAnnotation(roles = { RoleEnum.ADM })
  public ResponseEntity<?> deleteMeal(@PathVariable String id) {
    try {
      ticketService.delete(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
  }

}
