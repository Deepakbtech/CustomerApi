package com.abnamro.test.customer.service;

import com.abnamro.test.customer.models.Customer;

public interface CustomerService {

  Customer create(Customer customer);


  Customer retrieve(String userName);

}
