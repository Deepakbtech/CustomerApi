package com.abnamro.test.customer.repository;

import com.abnamro.test.customer.entities.CustomerCredentialsDbWrapper;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerCredentialsDbRepository extends
    JpaRepository<CustomerCredentialsDbWrapper, Long> {

  Optional<CustomerCredentialsDbWrapper> findByUserName(String userName);
}
