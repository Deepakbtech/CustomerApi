package com.abnamro.test.customer.controller;

import com.abnamro.test.customer.models.Customer;
import com.abnamro.test.customer.models.TokenResponse;
import com.abnamro.test.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;


  @PostMapping("/register")
  public ResponseEntity<TokenResponse> registerCustomer(@Valid @RequestBody Customer customer) {
    Customer created = customerService.create(customer);
    return ResponseEntity.ok(new TokenResponse(created.getCustomerCredentials().getUserName(),
        created.getCustomerCredentials().getPassword(), null));
  }

}
