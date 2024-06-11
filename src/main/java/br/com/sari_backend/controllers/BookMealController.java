package br.com.sari_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sari_backend.annotations.RoleAnnotation;
import br.com.sari_backend.dtos.bookMeal.BookMealDTO;
import br.com.sari_backend.models.BookMeal;
import br.com.sari_backend.models.enums.RoleEnum;
import br.com.sari_backend.services.IBookMealService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/student/book")
public class BookMealController {

  @Autowired
  private IBookMealService bookMealService;

  @PostMapping
  @RoleAnnotation(roles = { RoleEnum.ALUNO })
  public ResponseEntity<?> bookMeal(@RequestBody BookMealDTO data, HttpServletRequest request) {
    try {
      String email = (String) request.getAttribute("email");

      BookMeal bookedMeal = bookMealService.bookMeal(data.getMealId(), email);
      return new ResponseEntity<>(bookedMeal, HttpStatus.OK);

    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("student/cancel/{mealId}")
  @RoleAnnotation(roles = { RoleEnum.ALUNO })
  public ResponseEntity<?> cancelBook(@PathVariable String mealId, HttpServletRequest request) {
    try {
      String email = (String) request.getAttribute("email");

      bookMealService.cancelBook(mealId, email);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
