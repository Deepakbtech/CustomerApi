package com.abnamro.test.customer.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "address")
@Entity
@Data
public class AddressDbWrapper implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "id")
  long id;
  Integer houseNumber;
  String houseNumberExtension;
  String streetName;
  String postalCode;
  String city;
  String country;
  Instant createdAt;
  @OneToOne (mappedBy="address")
  CustomerDbWrapper customer;

}
