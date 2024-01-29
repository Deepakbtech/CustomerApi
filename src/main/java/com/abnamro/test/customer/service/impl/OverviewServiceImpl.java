package com.abnamro.test.customer.service.impl;

import com.abnamro.test.customer.exception.NotFoundException;
import com.abnamro.test.customer.exception.UnAuthorizedException;
import com.abnamro.test.customer.models.Account;
import com.abnamro.test.customer.models.Account.AccountDto;
import com.abnamro.test.customer.models.Customer;
import com.abnamro.test.customer.models.CustomerCredentials;
import com.abnamro.test.customer.service.CustomerService;
import com.abnamro.test.customer.service.OverviewService;
import com.abnamro.test.customer.util.AuthServiceUtil;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OverviewServiceImpl implements OverviewService {

  private final JwtTokenHelperService jwtTokenHelperService;

  private final CustomerService customerService;

  @Override
  public AccountDto retrieveAccountOverview() {

    String authorization = AuthServiceUtil.retrieveHeader("Authorization");
    if (authorization == null) {
      throw new UnAuthorizedException("Invalid token");
    }
    String token = authorization.substring(7);
    if (jwtTokenHelperService.isValidToken(token)) {
      Customer customer;
      try {
        customer = customerService.retrieve(
            jwtTokenHelperService.extractUsername(token));
      } catch (NotFoundException e) {
        throw new UnAuthorizedException("Invalid access");
      }

      //As of now stubbed the balance and type to the account;
      var account = Account.builder()
          .accountNumber(customer.getAccount().getAccountNumber())
          .ibanAccount(customer.getAccount().getIbanAccount())
          .balance(BigDecimal.valueOf(1000)).type("SavingsAccount").build();
      return account.toAccountDto();
    }
    throw new UnAuthorizedException("Invalid token");
  }

}
