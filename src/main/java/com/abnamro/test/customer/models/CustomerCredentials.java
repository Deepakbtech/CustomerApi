package com.abnamro.test.customer.models;


import static com.abnamro.test.customer.constants.CustomerConstants.ALPHANUMERIC;

import jakarta.validation.constraints.Pattern;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCredentials {

  @Pattern(message = "UserName contains invalid characters", regexp = ALPHANUMERIC)
  String userName;
  String password;
  Instant createdAt;
}
