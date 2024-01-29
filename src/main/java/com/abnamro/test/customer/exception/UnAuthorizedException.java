package com.abnamro.test.customer.exception;

public class UnAuthorizedException extends RuntimeException {

  public UnAuthorizedException(String message) {
    super(message);
  }
}
