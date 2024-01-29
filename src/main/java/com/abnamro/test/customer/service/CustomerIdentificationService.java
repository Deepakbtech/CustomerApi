package com.abnamro.test.customer.service;

import com.abnamro.test.customer.entities.CustomerIdentificationDbWrapper;
import com.abnamro.test.customer.models.CustomerIdentification;

public interface CustomerIdentificationService {

  CustomerIdentificationDbWrapper create(CustomerIdentification customerIdentification);
}
