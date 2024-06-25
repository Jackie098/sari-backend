package br.com.sari_backend.config.exceptions.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.sari_backend.config.exceptions.BadRequestException;
import br.com.sari_backend.config.exceptions.BusinessException;
import br.com.sari_backend.config.exceptions.DeniedPermissionException;
import br.com.sari_backend.config.exceptions.ResourceNotFoundException;
import br.com.sari_backend.config.exceptions.ResourcePersistenceException;

import br.com.sari_backend.config.exceptions.SubException;

@ControllerAdvice
@RestController
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({ Exception.class, ResourceNotFoundException.class, BadRequestException.class,
      BusinessException.class, ResourcePersistenceException.class, DeniedPermissionException.class,
  })
  public ResponseEntity<RestErrorMessage> handleExceptions(Exception ex, WebRequest request) {
    RestErrorMessage exceptionsResponse = new RestErrorMessage(ex.getMessage(), request.getDescription(false), ex);

    HttpStatus status = classifyException(ex);

    return new ResponseEntity<RestErrorMessage>(exceptionsResponse, status);
  }

  @Override
  @Nullable
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatusCode status, WebRequest request) {
    Class<?> clazz = ex.getClass();
    System.out.println(clazz);
    System.out.println(MethodArgumentNotValidException.class);

    List<SubException> subs = new ArrayList<>();
    if (clazz.equals(MethodArgumentNotValidException.class)) {
      MethodArgumentNotValidException castedEx = (MethodArgumentNotValidException) ex;

      castedEx.getBindingResult().getAllErrors().forEach((error) -> {
        String fieldName = ((FieldError) error).getField();
        String errorMessage = error.getDefaultMessage();

        subs.add(new SubException(fieldName, errorMessage));
      });
    }

    RestErrorMessage exceptionsResponse = new RestErrorMessage(ex.getMessage(), request.getDescription(false), subs,
        ex);

    HttpStatus statusC = classifyException(ex);
    return new ResponseEntity<Object>(exceptionsResponse, statusC);
  }

  private HttpStatus classifyException(Exception ex) {
    String exceptionsName = ex.getClass().getSimpleName();

    switch (exceptionsName) {
      case "ResourceNotFoundException":
        return HttpStatus.NOT_FOUND;
      case "BadRequestException":
        return HttpStatus.BAD_REQUEST;
      case "ResourcePersistenceException":
        return HttpStatus.CONFLICT;
      case "BusinessException":
        return HttpStatus.BAD_REQUEST;
      case "DeniedPermissionException":
        return HttpStatus.FORBIDDEN;
      default:
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
  }
}
