package com.abnamro.test.customer.config;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CountryListProperty {

  @Value("${allowed.countries}")
  List<String> allowedCountries;

  public Boolean hasPartOfAllowedCountries(String country) {
    return allowedCountries.contains(country);
  }
}
