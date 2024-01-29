package com.abnamro.test.customer.service.impl;

import static com.abnamro.test.customer.constants.CustomerConstants.DEFAULT_PASSWORD;

import com.abnamro.test.customer.entities.CustomerCredentialsDbWrapper;
import com.abnamro.test.customer.entities.CustomerDbWrapper;
import com.abnamro.test.customer.exception.NotFoundException;
import com.abnamro.test.customer.models.Customer;
import com.abnamro.test.customer.models.CustomerCredentials;
import com.abnamro.test.customer.repository.CustomerCredentialsDbRepository;
import com.abnamro.test.customer.service.CustomerCredentialsService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner.Mode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerCredentialsServiceImpl implements CustomerCredentialsService {

  private final CustomerCredentialsDbRepository customerCredentialsDbRepository;

  private final ModelMapper modelMapper;

  @Override
  public CustomerCredentialsDbWrapper create(String userName) {
    var customerCredentialsWrapper = new CustomerCredentialsDbWrapper();
    customerCredentialsWrapper.setUserName(userName);
    customerCredentialsWrapper.setPassword(DEFAULT_PASSWORD);
    return customerCredentialsDbRepository.save(
        customerCredentialsWrapper);
  }

  @Override
  public CustomerCredentials retrieve(String userName) {
    Optional<CustomerCredentialsDbWrapper> optionalCustomerCredentialsDbWrapper = customerCredentialsDbRepository.findByUserName(
        userName);

    if (optionalCustomerCredentialsDbWrapper.isEmpty()) {
      throw new NotFoundException("Customer not found");
    }
    CustomerCredentials customerCredentials = CustomerCredentials.builder().build();
    modelMapper.map(optionalCustomerCredentialsDbWrapper.get(), customerCredentials);
    return customerCredentials;
  }
}
