package com.abnamro.test.customer.controller;

import com.abnamro.test.customer.models.Account.AccountDto;
import com.abnamro.test.customer.service.AccountService;
import com.abnamro.test.customer.service.OverviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountOverviewController {


  private final OverviewService overviewService;

  @GetMapping("/overview")
  public ResponseEntity<AccountDto> retrieveOverview() {
    return ResponseEntity.ok(overviewService.retrieveAccountOverview());
  }
}
