package com.abnamro.test.customer.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

  @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleValidationConstraints(
      Exception ex, HttpServletRequest request,
      HttpServletResponse response) {

    if (ex instanceof jakarta.validation.ConstraintViolationException) {
      return createValidationExceptionResponse(
          (jakarta.validation.ConstraintViolationException) ex);
    } else if (ex instanceof MethodArgumentNotValidException) {
      return createValidationExceptionResponse(
          (MethodArgumentNotValidException) ex);
    } else if (ex instanceof DataIntegrityViolationException
        || ex instanceof ConstraintViolationException) {
      return new ResponseEntity<>(
          ApiError.builder().message("UserName exist, try different username")
              .code(HttpStatus.BAD_REQUEST.value()).build(), HttpStatus.BAD_REQUEST);
    } else if (ex instanceof UnAuthorizedException) {
      return new ResponseEntity<>(
          ApiError.builder().message(ex.getMessage())
              .code(HttpStatus.UNAUTHORIZED.value()).build(), HttpStatus.UNAUTHORIZED);
    } else if (ex instanceof NotFoundException) {
      return new ResponseEntity<>(
          ApiError.builder().message(ex.getMessage())
              .code(HttpStatus.NOT_FOUND.value()).build(), HttpStatus.NOT_FOUND);
    } else if (ex instanceof TooManyRequestException) {
      return new ResponseEntity<>(
          ApiError.builder().message(ex.getMessage())
              .code(HttpStatus.TOO_MANY_REQUESTS.value()).build(), HttpStatus.TOO_MANY_REQUESTS);
    }
    return createValidationExceptionResponse(ex);
  }

  private ResponseEntity createValidationExceptionResponse(
      jakarta.validation.ConstraintViolationException ex) {
    List<String> errors = ex.getConstraintViolations().stream().map(
        constraintViolation -> constraintViolation.getPropertyPath() + ":"
            + constraintViolation.getMessage()).toList();

    return new ResponseEntity<>(
        ApiError.builder().message(errors.toString())
            .code(HttpStatus.BAD_REQUEST.value()).build(), HttpStatus.BAD_REQUEST);
  }


  private ResponseEntity createValidationExceptionResponse(
      Exception ex) {
    return new ResponseEntity<>(
        ApiError.builder().message(ex.getMessage())
            .code(HttpStatus.BAD_REQUEST.value()).build(), HttpStatus.BAD_REQUEST);
  }

  private ResponseEntity createValidationExceptionResponse(MethodArgumentNotValidException ex) {
    List<String> errors = ex.getFieldErrors().stream().map(
        violation -> violation.getField() + ":"
            + violation.getDefaultMessage()).toList();

    return new ResponseEntity<>(
        ApiError.builder().message(errors.toString())
            .code(HttpStatus.BAD_REQUEST.value()).build(), HttpStatus.BAD_REQUEST);
  }


}
