package com.abnamro.test.customer.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.abnamro.test.customer.exception.NotFoundException;
import com.abnamro.test.customer.models.Address;
import com.abnamro.test.customer.models.Customer;
import com.abnamro.test.customer.models.CustomerIdentification;
import com.abnamro.test.customer.repository.CustomerDbRepository;
import com.abnamro.test.customer.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CustomerServiceTest {

  @Autowired
  private CustomerDbRepository customerDbRepository;

  @Mock
  private CustomerIdentificationService customerIdentificationService;
  @Mock
  private CustomerCredentialsService customerCredentialsService;
  @Mock
  private AddressService addressService;
  @Mock
  private AccountService accountService;


  CustomerService customerService;

  @BeforeEach
  public void setUp() {
    customerService = new CustomerServiceImpl(customerDbRepository, customerIdentificationService,
        customerCredentialsService, addressService, accountService, new ModelMapper());
  }

  @Test
  public void shouldCreateCustomer() {
    var credentials = customerService.create(
        Customer.builder().name("test").userName("test").address(
                Address.builder().country("NL").build())
            .customerIdentification(CustomerIdentification.builder().build()).build());
    assertNotNull(credentials);

    verify(addressService, times(1)).create(any());
    verify(accountService, times(1)).create();
    verify(customerIdentificationService, times(1)).create(any());
    verify(customerCredentialsService, times(1)).create(any());
  }

  @Test
  public void shouldFindByUserName() {
    customerService.create(
        Customer.builder().name("test").userName("test").address(
                Address.builder().country("NL").build())
            .customerIdentification(CustomerIdentification.builder().build()).build());
    var retrieved = customerService.retrieve("test");
    assertNotNull(retrieved);
  }

  @Test
  public void shouldThrowNotFoundIncaseUserNotFound() {
    assertThrows(NotFoundException.class, ()->customerService.retrieve("test1"));
  }

}
