package br.com.sari_backend.config.exceptions;

public class SubException {
  protected String object;
  protected String field;
  protected Object rejectedValue;
  protected String message;

  SubException(String object, String message) {
    this.object = object;
    this.message = message;
  }
}
