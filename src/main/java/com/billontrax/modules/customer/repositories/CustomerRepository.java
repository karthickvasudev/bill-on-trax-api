package com.billontrax.modules.customer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.billontrax.modules.customer.entities.Customer;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByCustomerCodeAndIsDeletedFalse(String customerCode);

    Optional<Customer> findByIdAndIsDeletedFalse(Long id);
}
