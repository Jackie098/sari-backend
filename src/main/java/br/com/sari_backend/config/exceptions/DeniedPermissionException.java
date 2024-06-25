package br.com.sari_backend.config.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class DeniedPermissionException extends RuntimeException {
  // private static final long serialVersionUID = 1L;

  public DeniedPermissionException(String ex) {
    super(ex);
  }
}
