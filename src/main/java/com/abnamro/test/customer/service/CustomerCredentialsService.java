package com.abnamro.test.customer.service;

import com.abnamro.test.customer.entities.CustomerCredentialsDbWrapper;
import com.abnamro.test.customer.models.CustomerCredentials;

public interface CustomerCredentialsService {

  CustomerCredentialsDbWrapper create(String userName);

  CustomerCredentials retrieve(String userName);

}
