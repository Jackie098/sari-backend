package br.com.sari_backend.config.exceptions;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;

@Data
public class ExceptionResponse {
  @JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
  private LocalDateTime timestamp;
  private String message;
  private String debugMessage;
  private String details;
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
    this.details = details;
    this.message = message;
    this.debugMessage = ex.toString();
  }
}
