package com.abnamro.test.customer.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.abnamro.test.customer.repository.CustomerCredentialsDbRepository;
import com.abnamro.test.customer.service.impl.CustomerCredentialsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CustomerCredentialsServiceTest {

  @Autowired
  CustomerCredentialsDbRepository customerCredentialsDbRepository;

  CustomerCredentialsService customerCredentialsService;

  @BeforeEach
  public void setUp() {
    customerCredentialsService = new CustomerCredentialsServiceImpl(
        customerCredentialsDbRepository, new ModelMapper());
  }

  @Test
  public void shouldCreateCredentials() {
    var credentials = customerCredentialsService.create("test");
    assertNotNull(credentials);
    assertNotNull(credentials.getPassword());
  }

  @Test
  public void shouldRetrieveCredentials() {
    customerCredentialsService.create("test");
    var credentials = customerCredentialsService.retrieve("test");
    assertNotNull(credentials);
    assertNotNull(credentials.getPassword());
  }
}
