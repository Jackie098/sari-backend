package br.com.sari_backend.config.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.persistence.PersistenceException;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class ResourcePersistenceException extends PersistenceException {
  public ResourcePersistenceException(String ex) {
    super(ex);
  }
}
