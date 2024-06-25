package br.com.sari_backend.config.exceptions;

import lombok.Data;

@Data
public class SubException {
  protected String object;
  protected String field;
  protected Object rejectedValue;
  protected String message;

  public SubException(String object, String message) {
    this.object = object;
    this.message = message;
  }

  public SubException(String object, String message, Object rejectedValue) {
    this.object = object;
    this.message = message;
    this.rejectedValue = rejectedValue;
  }

  public SubException(String field, String object, String message, Object rejectedValue) {
    this.object = object;
    this.field = field;
    this.message = message;
    this.rejectedValue = rejectedValue;
  }
}
