package com.abnamro.test.customer.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "customerIdentification")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerIdentificationDbWrapper  implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "id")
  long id;
  String type;
  String details;
  String otp;
  Instant createdAt;
  @OneToOne(mappedBy="customerIdentification")
  CustomerDbWrapper customer;
}
