package com.abnamro.test.customer.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.abnamro.test.customer.models.Address;
import com.abnamro.test.customer.repository.AddressDbRepository;
import com.abnamro.test.customer.service.impl.AddressServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class AddressServiceTest {

  @Autowired
  private AddressDbRepository addressDbRepository;

  AddressService addressService;

  @BeforeEach
  public void setUp() {
    addressService = new AddressServiceImpl(addressDbRepository, new ModelMapper());
  }

  @Test
  public void shouldCreateAddress() {
    var address = addressService.create(
        Address.builder().country("NL").city("Utrecht").postalCode("2112RR").houseNumber(12)
            .build());

    assertNotNull(address);
  }

}
