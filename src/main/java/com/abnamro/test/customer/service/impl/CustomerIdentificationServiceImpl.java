package com.abnamro.test.customer.service.impl;

import com.abnamro.test.customer.entities.CustomerIdentificationDbWrapper;
import com.abnamro.test.customer.models.CustomerIdentification;
import com.abnamro.test.customer.repository.CustomerIdentificationDbRepository;
import com.abnamro.test.customer.service.CustomerIdentificationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerIdentificationServiceImpl implements CustomerIdentificationService {

  private final ModelMapper modelMapper;

  private final CustomerIdentificationDbRepository customerIdentificationDbRepository;

  @Override
  public CustomerIdentificationDbWrapper create(CustomerIdentification customerIdentification) {
    var customerIdentificationWrapper = new CustomerIdentificationDbWrapper();
    modelMapper.map(customerIdentification, customerIdentificationWrapper);
    return customerIdentificationDbRepository.save(
        customerIdentificationWrapper);
  }
}
