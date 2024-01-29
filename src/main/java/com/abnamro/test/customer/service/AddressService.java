package com.abnamro.test.customer.service;

import com.abnamro.test.customer.entities.AddressDbWrapper;
import com.abnamro.test.customer.models.Address;

public interface AddressService {

  AddressDbWrapper create(Address address);

}
