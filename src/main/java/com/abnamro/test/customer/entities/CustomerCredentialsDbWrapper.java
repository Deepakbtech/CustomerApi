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

@Data
@Table(name = "customerCredentials")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerCredentialsDbWrapper  implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "id")
  long id;
  @Column(unique = true)
  String userName;
  String password;
  Instant createdAt;
  @OneToOne (mappedBy="customerCredentials")
  CustomerDbWrapper customer;
}
