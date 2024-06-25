package br.com.sari_backend.config.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.sari_backend.config.exceptions.BadRequestException;
import br.com.sari_backend.config.exceptions.BusinessException;
import br.com.sari_backend.config.exceptions.DeniedPermissionException;
import br.com.sari_backend.config.exceptions.ExceptionResponse;
import br.com.sari_backend.config.exceptions.ResourceNotFoundException;
import br.com.sari_backend.config.exceptions.ResourcePersistenceException;

// TODO: Add handling for body and path requests validation
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({ Exception.class, ResourceNotFoundException.class, BadRequestException.class,
      BusinessException.class,
      ResourcePersistenceException.class, DeniedPermissionException.class })
  public final ResponseEntity<ExceptionResponse> handleExceptions(Exception ex, WebRequest request) {
    ExceptionResponse expcetionsResponse = new ExceptionResponse(ex.getMessage(), request.getDescription(false), ex);

    HttpStatus status = classifyException(ex);

    return new ResponseEntity<ExceptionResponse>(expcetionsResponse, status);
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
