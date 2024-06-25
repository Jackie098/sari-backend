package br.com.sari_backend.config.exceptions.handler;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import br.com.sari_backend.config.exceptions.SubException;
import lombok.Data;

@Data
public class ExceptionResponse {
  @JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
  private LocalDateTime timestamp;
  private String message;
  private String debugMessage;
  private String detail;
  private List<SubException> subsErrors;

  public ExceptionResponse() {
    timestamp = LocalDateTime.now();
  }

  public ExceptionResponse(Throwable ex) {
    this();
    this.message = "Unexpected error";
    this.debugMessage = ex.getLocalizedMessage();
  }

  public ExceptionResponse(String message, String details, Throwable ex) {
    this();
    this.detail = details;
    this.message = message;
    this.debugMessage = ex.toString();
  }

  public ExceptionResponse(String message, String details, List<SubException> subErrors, Throwable ex) {
    this();
    this.detail = details;
    this.message = message;
    this.debugMessage = ex.toString();
    this.subsErrors = subErrors;
  }
}
