package com.abnamro.test.customer.service.impl;

import com.abnamro.test.customer.entities.AccountDbWrapper;
import com.abnamro.test.customer.entities.AddressDbWrapper;
import com.abnamro.test.customer.entities.CustomerCredentialsDbWrapper;
import com.abnamro.test.customer.entities.CustomerDbWrapper;
import com.abnamro.test.customer.entities.CustomerIdentificationDbWrapper;
import com.abnamro.test.customer.exception.NotFoundException;
import com.abnamro.test.customer.models.Customer;
import com.abnamro.test.customer.repository.CustomerDbRepository;
import com.abnamro.test.customer.service.AccountService;
import com.abnamro.test.customer.service.AddressService;
import com.abnamro.test.customer.service.CustomerCredentialsService;
import com.abnamro.test.customer.service.CustomerIdentificationService;
import com.abnamro.test.customer.service.CustomerService;
import java.time.Clock;
import java.time.Instant;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerDbRepository customerDbRepository;

  private final CustomerIdentificationService customerIdentificationService;

  private final CustomerCredentialsService customerCredentialsService;

  private final AddressService addressService;

  private final AccountService accountService;

  private final ModelMapper modelMapper;


  @Override
  @Transactional
  public Customer create(Customer customer) {
    var createdAccount = accountService.create();

    var createdAddress = addressService.create(customer.getAddress());

    var customerIdentificationWrapper = customerIdentificationService.create(
        customer.getCustomerIdentification());
    var customerCredentialsWrapper = customerCredentialsService.create(customer.getUserName());
    var createdCustomer = getCustomerDbWrapper(customer, createdAccount, createdAddress,
        customerIdentificationWrapper, customerCredentialsWrapper);

    modelMapper.map(createdCustomer, customer);
    return customer;
  }

  @Override
  public Customer retrieve(String userName) {
    Optional<CustomerDbWrapper> optCustomerDbWrapper = customerDbRepository.findCustomerByUserName(
        userName);

    if (optCustomerDbWrapper.isEmpty()) {
      throw new NotFoundException("Customer not found");
    }
    Customer customer = Customer.builder().build();
    modelMapper.map(optCustomerDbWrapper.get(), customer);
    return customer;
  }

  private CustomerDbWrapper getCustomerDbWrapper(Customer customer, AccountDbWrapper createdAccount,
      AddressDbWrapper createdAddress,
      CustomerIdentificationDbWrapper createdCustomerIdentification,
      CustomerCredentialsDbWrapper customerCredentialsWrapper) {
    var customerWrapper = new CustomerDbWrapper();
    modelMapper.map(customer, customerWrapper);
    customerWrapper.setAccount(createdAccount);
    customerWrapper.setAddress(createdAddress);
    customerWrapper.setCustomerIdentification(createdCustomerIdentification);
    customerWrapper.setCustomerCredentials(customerCredentialsWrapper);
    customerWrapper.setCreatedAt(Instant.now(Clock.systemUTC()));
    return customerDbRepository.save(customerWrapper);
  }

}
