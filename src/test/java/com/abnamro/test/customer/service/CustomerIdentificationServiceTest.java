package com.abnamro.test.customer.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.abnamro.test.customer.models.CustomerIdentification;
import com.abnamro.test.customer.repository.CustomerIdentificationDbRepository;
import com.abnamro.test.customer.service.impl.CustomerIdentificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CustomerIdentificationServiceTest {

  @Autowired
  private CustomerIdentificationDbRepository customerIdentificationDbRepository;

  CustomerIdentificationService customerIdentificationService;

  @BeforeEach
  public void setUp() {
    customerIdentificationService = new CustomerIdentificationServiceImpl(new ModelMapper(),
        customerIdentificationDbRepository);
  }

  @Test
  public void shouldCreateCustomerIdentification() {
    var customerIdentification = customerIdentificationService.create(
        CustomerIdentification.builder().type("Passport").details("EWEWEWEWEW").build());
    assertNotNull(customerIdentification);
  }
}
