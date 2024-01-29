package com.abnamro.test.customer.repository;

import com.abnamro.test.customer.entities.CustomerDbWrapper;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDbRepository extends JpaRepository<CustomerDbWrapper, Long> {

  Optional<CustomerDbWrapper> findCustomerByUserName(String userName);
}
