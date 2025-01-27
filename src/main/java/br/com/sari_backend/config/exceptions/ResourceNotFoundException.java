package br.com.sari_backend.config.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
  // private static final long serialVersionUID = 1L;

  public ResourceNotFoundException(String ex) {
    super(ex);
  }
}
