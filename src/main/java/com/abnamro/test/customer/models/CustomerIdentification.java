package com.abnamro.test.customer.models;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerIdentification {

  String type;
  String details;
  String otp;
  Instant createdAt;
}
