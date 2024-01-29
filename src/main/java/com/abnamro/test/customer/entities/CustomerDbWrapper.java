package com.abnamro.test.customer.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
@Entity
@Builder(toBuilder = true)
public class CustomerDbWrapper  implements Serializable {

  @Id
  @GeneratedValue
  long id;

  String name;
  @Column(unique = true)
  String userName;
  LocalDate dateOfBirth;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "address_Id", referencedColumnName = "id")
  AddressDbWrapper address;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "account_Id", referencedColumnName = "id")
  AccountDbWrapper account;
  @OneToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "customerCredentials_id", referencedColumnName = "id")
  CustomerCredentialsDbWrapper customerCredentials;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "customerIdentification_id", referencedColumnName = "id")
  CustomerIdentificationDbWrapper customerIdentification;
  Instant createdAt;

}
