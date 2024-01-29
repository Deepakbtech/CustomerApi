package com.abnamro.test.customer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.abnamro.test.customer.entities.AccountDbWrapper;
import com.abnamro.test.customer.exception.NotFoundException;
import com.abnamro.test.customer.exception.UnAuthorizedException;
import com.abnamro.test.customer.models.Account;
import com.abnamro.test.customer.models.Account.AccountDto;
import com.abnamro.test.customer.models.Customer;
import com.abnamro.test.customer.repository.AccountDbRepository;
import com.abnamro.test.customer.service.impl.AccountServiceImpl;
import com.abnamro.test.customer.service.impl.JwtTokenHelperService;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@DataJpaTest
public class AccountServiceTest {

  @Autowired
  private AccountDbRepository accountDbRepository;

  AccountService accountService;

  @BeforeEach
  public void setUp() {
    accountService = new AccountServiceImpl(accountDbRepository, new ModelMapper(), new Random());
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader("Authorization", "TestAuthorizationValue");
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
  }

  @Test
  public void shouldCreateAccount() {
    AccountDbWrapper account = accountService.create();

    assertNotNull(account);
    assertNotNull(account.getAccountNumber());
    assertNotNull(account.getIbanAccount());
  }


}
