package com.abnamro.test.customer.models;

import static com.abnamro.test.customer.constants.CustomerConstants.ALPHABETS;
import static com.abnamro.test.customer.constants.CustomerConstants.ALPHANUMERIC;
import static com.abnamro.test.customer.constants.CustomerConstants.FREE_TEXT_REGEX;
import static com.abnamro.test.customer.constants.CustomerConstants.POSTAL_CODE;

import com.abnamro.test.customer.validation.ValidCountry;
import jakarta.validation.constraints.Pattern;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

  Integer houseNumber;
  @Pattern(message = "House number extension contains invalid characters", regexp = ALPHANUMERIC)
  String houseNumberExtension;
  @Pattern(message = "Street Name contains invalid characters", regexp = FREE_TEXT_REGEX)
  String streetName;

  @Pattern(message = "Postal code contains invalid characters", regexp = POSTAL_CODE)
  String postalCode;
  @Pattern(message = "City name contains invalid characters", regexp = ALPHABETS)
  String city;

  @ValidCountry
  @Pattern(message = "Country name contains invalid characters", regexp = ALPHABETS)
  String country;

  Instant createdAt;

}
