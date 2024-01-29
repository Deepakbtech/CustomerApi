package com.abnamro.test.customer.models;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

  String accountNumber;
  String ibanAccount;
  BigDecimal balance;
  String type;
  Instant createdAt;
  Customer customer;

  public AccountDto toAccountDto() {
    return new AccountDto(accountNumber, ibanAccount, balance, type);
  }

  @Value
  public static class AccountDto {

    String accountNumber;
    String ibanAccount;
    BigDecimal balance;
    String type;
  }
}
