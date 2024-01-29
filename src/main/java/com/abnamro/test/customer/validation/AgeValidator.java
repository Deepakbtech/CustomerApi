package com.abnamro.test.customer.validation;

import static com.abnamro.test.customer.constants.CustomerConstants.EIGHTEEN_YEARS_IN_MONTHS;
import static java.time.ZoneOffset.UTC;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class AgeValidator implements ConstraintValidator<ValidAge, LocalDate> {

  @Override
  public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }
    var calculatedDate = value.plusMonths(EIGHTEEN_YEARS_IN_MONTHS);
    return calculatedDate.isBefore(LocalDate.now(UTC)) || calculatedDate.isEqual(
        LocalDate.now(UTC));
  }
}
