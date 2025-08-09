package com.billontrax.modules.customer.services;

import com.billontrax.modules.customer.dtos.CustomerContactDto;
import com.billontrax.modules.customer.dtos.CustomerCreateRequest;
import com.billontrax.modules.customer.dtos.CustomerDto;
import com.billontrax.modules.customer.dtos.CustomerUpdateRequest;

public interface CustomerService {
    CustomerDto createCustomer(CustomerCreateRequest request);

    CustomerDto updateCustomer(Long id, CustomerUpdateRequest request);

    CustomerDto getCustomerById(Long id);

    CustomerDto getCustomerByCode(String customerCode);

    void softDeleteCustomer(Long id);

    void updateCustomerContact(String code, CustomerContactDto body);

    void deleteCustomerContact(String code, Long contactId);
}
