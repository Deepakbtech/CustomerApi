package com.abnamro.test.customer.service.impl;

import com.abnamro.test.customer.entities.AccountDbWrapper;
import com.abnamro.test.customer.exception.NotFoundException;
import com.abnamro.test.customer.exception.UnAuthorizedException;
import com.abnamro.test.customer.models.Account;
import com.abnamro.test.customer.models.Account.AccountDto;
import com.abnamro.test.customer.models.CustomerCredentials;
import com.abnamro.test.customer.repository.AccountDbRepository;
import com.abnamro.test.customer.service.AccountService;
import com.abnamro.test.customer.service.CustomerCredentialsService;
import com.abnamro.test.customer.util.AuthServiceUtil;
import com.abnamro.test.customer.util.IbanConversionUtil;
import java.math.BigDecimal;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

  private final AccountDbRepository accountDbRepository;

  private final ModelMapper modelMapper;

  private final Random random;


  public AccountDbWrapper create() {
    var accountNumber = IbanConversionUtil.generateBban(random.nextLong());
    var ibanAccount = IbanConversionUtil.toIban(accountNumber);

    var account = Account.builder()
        .accountNumber(IbanConversionUtil.generateBban(random.nextLong()))
        .ibanAccount(ibanAccount).type("Savings").build();

    var accountDbWrapper = new AccountDbWrapper();
    modelMapper.map(account, accountDbWrapper);
    return accountDbRepository.save(accountDbWrapper);
  }

}
