package com.billontrax.modules.customer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.billontrax.modules.customer.entities.CustomerContact;

import jakarta.transaction.Transactional;

public interface CustomerContactRepository extends JpaRepository<CustomerContact, Long> {

    @Transactional
    void deleteByIdAndCustomerId(Long contactId, Long customerId);

}
