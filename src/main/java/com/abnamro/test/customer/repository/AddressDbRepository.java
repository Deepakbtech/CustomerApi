package com.abnamro.test.customer.repository;

import com.abnamro.test.customer.entities.AddressDbWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressDbRepository extends JpaRepository<AddressDbWrapper, Long> {

}
