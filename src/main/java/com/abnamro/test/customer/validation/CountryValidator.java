package com.abnamro.test.customer.validation;

import com.abnamro.test.customer.config.ApplicationContextHolder;
import com.abnamro.test.customer.config.CountryListProperty;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class CountryValidator implements ConstraintValidator<ValidCountry, String> {

  @Autowired
  CountryListProperty countryListProperty;


  public CountryValidator() {
    ApplicationContextHolder.autowireBean(this);
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }
    return countryListProperty.hasPartOfAllowedCountries(value);
  }


}
