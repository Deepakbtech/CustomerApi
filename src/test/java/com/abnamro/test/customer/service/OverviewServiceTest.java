package com.abnamro.test.customer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.abnamro.test.customer.exception.NotFoundException;
import com.abnamro.test.customer.exception.UnAuthorizedException;
import com.abnamro.test.customer.models.Account;
import com.abnamro.test.customer.models.Account.AccountDto;
import com.abnamro.test.customer.models.Customer;
import com.abnamro.test.customer.service.impl.JwtTokenHelperService;
import com.abnamro.test.customer.service.impl.OverviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ExtendWith(SpringExtension.class)
public class OverviewServiceTest {

  OverviewService overviewService;

  @Mock
  private CustomerService customerService;
  @Mock
  private JwtTokenHelperService jwtTokenService;


  @BeforeEach
  public void setUp() {
    overviewService = new OverviewServiceImpl(jwtTokenService, customerService);
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader("Authorization", "TestAuthorizationValue");
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
  }

  @Test
  public void shouldRetrieveOverview() {
    when(jwtTokenService.isValidToken(any())).thenReturn(true);
    when(customerService.retrieve(any())).thenReturn(Customer.builder().account(
            Account.builder().accountNumber("6771015777").ibanAccount("NL13ABNA6771015777").build())
        .build());
    AccountDto account = overviewService.retrieveAccountOverview();

    assertNotNull(account);
    assertEquals("6771015777", account.getAccountNumber());
    assertEquals("NL13ABNA6771015777", account.getIbanAccount());
    assertNotNull(account.getBalance());
  }

  @Test
  public void shouldThrowUnAuthorizedIncaseOfInvalidToken() {
    when(jwtTokenService.isValidToken(any())).thenReturn(false);
    assertThrows(UnAuthorizedException.class, () -> overviewService.retrieveAccountOverview());
  }

  @Test
  public void shouldThrowUnAuthorizedWhenUserNotFound() {
    when(jwtTokenService.isValidToken(any())).thenReturn(true);
    when(customerService.retrieve(any())).thenThrow(new NotFoundException("Not found"));
    assertThrows(UnAuthorizedException.class, () -> overviewService.retrieveAccountOverview());
  }

}
