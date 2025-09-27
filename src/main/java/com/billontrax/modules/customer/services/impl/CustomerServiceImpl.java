package com.billontrax.modules.customer.services.impl;

import java.util.Optional;

import com.billontrax.modules.core.customfields.enums.CustomFieldModule;
import com.billontrax.modules.core.customfields.service.CustomFieldService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.billontrax.common.config.CurrentUserHolder;
import com.billontrax.common.exceptions.ErrorMessageException;
import com.billontrax.common.utils.DateUtil;
import com.billontrax.modules.customer.dtos.CustomerContactDto;
import com.billontrax.modules.customer.dtos.CustomerCreateRequest;
import com.billontrax.modules.customer.dtos.CustomerDto;
import com.billontrax.modules.customer.dtos.CustomerUpdateRequest;
import com.billontrax.modules.customer.entities.Customer;
import com.billontrax.modules.customer.entities.CustomerContact;
import com.billontrax.modules.customer.mappers.CustomerMapper;
import com.billontrax.modules.customer.repositories.CustomerContactRepository;
import com.billontrax.modules.customer.repositories.CustomerRepository;
import com.billontrax.modules.customer.services.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerContactRepository customerContactRepository;
    private final CustomerMapper customerMapper;
    private final CustomFieldService customFieldService;

    @Override
    @Transactional
    public CustomerDto createCustomer(CustomerCreateRequest request) {
		log.info("createCustomer request: {}", request);
        try {
            Customer entity = customerMapper.toEntity(request);
            entity.setIsDeleted(false);
            entity.setBusinessId(CurrentUserHolder.getBusinessId());
            Customer saved = customerRepository.save(entity);
            String code = String.format("CUST-%s-%d", DateUtil.formatyyyyMMdd(entity.getCreatedTime()), saved.getId());
            saved.setCustomerCode(code);
            CustomerDto savedCustomer = customerMapper.toDto(customerRepository.save(saved));
            customFieldService.saveFieldValues(CustomFieldModule.Customer, savedCustomer.getId(), request.getCustomFields());
            return savedCustomer;
        } catch (DataIntegrityViolationException e) {
            throw new ErrorMessageException(
                    String.format("Customer with the phone number %s already exists", request.getPhone()));
        }
    }

    @Override
    @Transactional
    public CustomerDto updateCustomer(Long id, CustomerUpdateRequest request) {
        Customer entity = customerRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customerMapper.updateEntityFromDto(request, entity);
        Customer updated = customerRepository.save(entity);
        return customerMapper.toDto(updated);
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findByIdAndIsDeletedFalse(id);
        return optionalCustomer.map(customerMapper::toDto)
                .orElseThrow(() -> new ErrorMessageException("Customer not found"));

    }

    @Override
    public CustomerDto getCustomerByCode(String customerCode) {
        return customerRepository.findByCustomerCodeAndIsDeletedFalse(customerCode)
                .map(customerMapper::toDto)
                .orElseThrow(() -> new ErrorMessageException("Customer not found"));
    }

    @Override
    @Transactional
    public void softDeleteCustomer(Long id) {
        Customer entity = customerRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        entity.setIsDeleted(true);
        customerRepository.save(entity);
    }

    @Override
    public void updateCustomerContact(String code, CustomerContactDto body) {
        Customer customer = customerRepository.findByCustomerCodeAndIsDeletedFalse(code)
                .orElseThrow(() -> new ErrorMessageException("Customer not found"));
        CustomerContact contact = new CustomerContact();

        customerMapper.updateContactFromDto(body, contact);
        contact.setCustomer(customer);
        customerContactRepository.save(contact);
    }

    @Override
    public void deleteCustomerContact(String code, Long contactId) {
        Customer customer = customerRepository.findByCustomerCodeAndIsDeletedFalse(code)
                .orElseThrow(() -> new ErrorMessageException("Customer not found"));
        customer.getContacts().stream()
                .filter(contact -> contact.getId().equals(contactId))
                .findFirst().ifPresentOrElse(
                        contact -> customer.removeContact(contact),
                        () -> {
                            throw new ErrorMessageException("Contact not found");
                        });
        customerRepository.save(customer);
    }

}
