package com.abnamro.test.customer.exception;

public class TooManyRequestException extends RuntimeException {

  public TooManyRequestException(String message) {
    super(message);
  }
}
