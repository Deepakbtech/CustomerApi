package com.abnamro.test.customer.repository;

import com.abnamro.test.customer.entities.AccountDbWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDbRepository extends JpaRepository<AccountDbWrapper, Long> {

}
