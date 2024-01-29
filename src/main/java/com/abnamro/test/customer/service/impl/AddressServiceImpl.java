package com.abnamro.test.customer.service.impl;

import com.abnamro.test.customer.entities.AddressDbWrapper;
import com.abnamro.test.customer.models.Address;
import com.abnamro.test.customer.repository.AddressDbRepository;
import com.abnamro.test.customer.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

  private final AddressDbRepository addressDbRepository;

  private final ModelMapper modelMapper;

  @Override
  public AddressDbWrapper create(Address address) {
    var addressWrapper = new AddressDbWrapper();
    modelMapper.map(address, addressWrapper);
    return addressDbRepository.save(addressWrapper);
  }
}
