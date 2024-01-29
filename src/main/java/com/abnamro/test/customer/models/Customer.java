package com.abnamro.test.customer.models;

import static com.abnamro.test.customer.constants.CustomerConstants.ALPHANUMERIC;

import com.abnamro.test.customer.validation.ValidAge;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.Instant;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

  @NotNull
  @Pattern(message = "Name contains invalid characters", regexp = ALPHANUMERIC)
  String name;
  @NotNull
  @Pattern(message = "UserName contains invalid characters", regexp = ALPHANUMERIC)
  @Column(unique = true)
  String userName;
  @ValidAge
  LocalDate dateOfBirth;
  @Valid
  Address address;
  Account account;
  CustomerCredentials customerCredentials;
  CustomerIdentification customerIdentification;
  Instant createdAt;

}
