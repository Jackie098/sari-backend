package br.com.sari_backend.config.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BusinessException extends RuntimeException {
  public BusinessException(String ex) {
    super(ex);
  }
}