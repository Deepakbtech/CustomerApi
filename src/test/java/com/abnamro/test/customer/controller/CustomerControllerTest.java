package com.abnamro.test.customer.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.abnamro.test.customer.models.Address;
import com.abnamro.test.customer.models.Customer;
import com.abnamro.test.customer.models.CustomerIdentification;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class CustomerControllerTest {

  @Autowired
  CustomerController controller;


  @Test
  public void shouldRegisterCustomer() {
    var credentials = controller.registerCustomer(
        Customer.builder().name("test").userName("test").address(
                Address.builder().country("NL").build()).dateOfBirth(LocalDate.now().minusYears(20))
            .customerIdentification(
                CustomerIdentification.builder().type("Passport").build()).build());

    assertNotNull(credentials.getBody());
    assertNotNull(credentials.getBody().userName());
    assertNotNull(credentials.getBody().password());
  }

  @Test
  public void shouldRegisterCustomerWithExistingUserName() {
    var credentials = controller.registerCustomer(
        Customer.builder().name("test").userName("test1").address(
                Address.builder().country("NL").build()).dateOfBirth(LocalDate.now().minusYears(20))
            .customerIdentification(
                CustomerIdentification.builder().type("Passport").build()).build());

    assertNotNull(credentials.getBody());
    assertNotNull(credentials.getBody().userName());
    assertNotNull(credentials.getBody().password());

    assertThrows(DataIntegrityViolationException.class, () -> controller.registerCustomer(
        Customer.builder().name("test").userName("test1").address(
                Address.builder().country("NL").build()).dateOfBirth(LocalDate.now().minusYears(20))
            .customerIdentification(
                CustomerIdentification.builder().type("Passport").build()).build()));
  }
}
