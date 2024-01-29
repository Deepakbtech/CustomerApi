package com.abnamro.test.customer.repository;

import com.abnamro.test.customer.entities.CustomerIdentificationDbWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerIdentificationDbRepository extends
    JpaRepository<CustomerIdentificationDbWrapper, Long> {

}
