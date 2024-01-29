package com.abnamro.test.customer.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.abnamro.test.customer.exception.UnAuthorizedException;
import com.abnamro.test.customer.models.Address;
import com.abnamro.test.customer.models.Customer;
import com.abnamro.test.customer.models.CustomerIdentification;
import com.abnamro.test.customer.service.CustomerService;
import com.abnamro.test.customer.service.impl.JwtTokenHelperService;
import java.time.LocalDate;
import net.bytebuddy.asm.Advice.Local;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class AccountControllerTest {

  @Autowired
  AccountOverviewController accountController;

  @Autowired
  JwtTokenHelperService jwtTokenHelperService;

  @Autowired
  CustomerService customerService;


  @BeforeEach
  public void setUp() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader("Authorization", "Bearer " + jwtTokenHelperService.generateToken("test"));
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
  }

  @Test
  public void shouldRetrieveAccount() {
    customerService.create(Customer.builder().name("test").userName("test").address(
            Address.builder().country("NL").build()).dateOfBirth(LocalDate.now().minusYears(20))
        .customerIdentification(
            CustomerIdentification.builder().type("Passport").build()).build());
    assertNotNull(accountController.retrieveOverview());
  }

  @Test
  public void shouldThrowExceptionIncaseOfInvalidToken() {
    customerService.create(Customer.builder().name("test").userName("test1").address(
            Address.builder().country("NL").build()).dateOfBirth(LocalDate.now().minusYears(20))
        .customerIdentification(
            CustomerIdentification.builder().type("Passport").build()).build());
    assertThrows(UnAuthorizedException.class, () -> accountController.retrieveOverview());
  }

}
