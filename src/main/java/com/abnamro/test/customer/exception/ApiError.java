package com.abnamro.test.customer.exception;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ApiError {

  int code;
  String message;

}
