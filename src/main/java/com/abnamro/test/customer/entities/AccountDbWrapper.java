package com.abnamro.test.customer.entities;

import com.abnamro.test.customer.models.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
@Entity
@Data
@Builder
public class AccountDbWrapper implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "id")
  long id;
  String accountNumber;
  String ibanAccount;
  BigDecimal balance;
  String type;
  Instant createdAt;
  @OneToOne (mappedBy="account")
  CustomerDbWrapper customer;
}
