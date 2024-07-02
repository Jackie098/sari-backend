package br.com.sari_backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sari_backend.annotations.RoleAnnotation;
import br.com.sari_backend.dtos.bookMeal.BookMealDTO;
import br.com.sari_backend.dtos.bookMeal.CreateBookMealDTO;
import br.com.sari_backend.mappers.GenericMapper;
import br.com.sari_backend.models.BookMeal;
import br.com.sari_backend.models.enums.RoleEnum;
import br.com.sari_backend.services.contracts.IBookMealService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/book")
public class BookMealController {

  private GenericMapper mapper;

  @Autowired
  private IBookMealService bookMealService;

  BookMealController() {
    this.mapper = GenericMapper.getInstance();
  }

  @GetMapping
  @RoleAnnotation(roles = { RoleEnum.ADM, RoleEnum.SERVIDOR })
  public ResponseEntity<?> listAllBooks() {
    List<BookMeal> bookMeals = bookMealService.findAll();

    List<BookMealDTO> mappedBookMeals = mapper.toList(bookMeals, BookMealDTO.class);

    return new ResponseEntity<>(mappedBookMeals, HttpStatus.OK);
  }

  @GetMapping("/student")
  @RoleAnnotation(roles = { RoleEnum.ALUNO })
  public ResponseEntity<?> listAllBooksByUser(HttpServletRequest request) {
    String email = (String) request.getAttribute("email");

    List<BookMeal> bookedMealsByUser = bookMealService.findAllByUser(email);

    List<BookMealDTO> mappedBookMeals = mapper.toList(bookedMealsByUser, BookMealDTO.class);

    return new ResponseEntity<>(mappedBookMeals, HttpStatus.OK);
  }

  @PostMapping
  @RoleAnnotation(roles = { RoleEnum.ALUNO })
  public ResponseEntity<?> bookMeal(@RequestBody CreateBookMealDTO data, HttpServletRequest request) {
    String email = (String) request.getAttribute("email");

    BookMeal bookedMeal = bookMealService.bookMeal(data.getMealId(), email);

    BookMealDTO dto = mapper.toObject(bookedMeal, BookMealDTO.class, true);

    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  @PatchMapping("/student/{studentId}/checkin")
  @RoleAnnotation(roles = { RoleEnum.SERVIDOR })
  public ResponseEntity<?> checkInStudent(@PathVariable String studentId) {
    BookMeal checkedBook = bookMealService.checkInStudent(studentId);

    BookMealDTO dto = mapper.toObject(checkedBook, BookMealDTO.class);

    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  @DeleteMapping("cancel/{mealId}")
  @RoleAnnotation(roles = { RoleEnum.ALUNO })
  public ResponseEntity<?> cancelBook(@PathVariable String mealId, HttpServletRequest request) {
    String email = (String) request.getAttribute("email");

    bookMealService.cancelBook(mealId, email);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
