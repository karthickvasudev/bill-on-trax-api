package com.billontrax.modules.customer.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.billontrax.common.dtos.Response;
import com.billontrax.common.dtos.ResponseStatus;
import com.billontrax.common.enums.ResponseCode;
import com.billontrax.modules.customer.dtos.CustomerContactDto;
import com.billontrax.modules.customer.dtos.CustomerCreateRequest;
import com.billontrax.modules.customer.dtos.CustomerDto;
import com.billontrax.modules.customer.dtos.CustomerUpdateRequest;
import com.billontrax.modules.customer.services.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("create")
    public Response<CustomerDto> createCustomer(@Valid @RequestBody CustomerCreateRequest request) {
        Response<CustomerDto> response = new Response<>(ResponseStatus.of(ResponseCode.OK_NOTIFY, "Customer created successfully"));
        CustomerDto dto = customerService.createCustomer(request);
        response.setData(dto);
        return response;
    }

    @PutMapping("{id}")
    public Response<CustomerDto> updateCustomer(@PathVariable Long id, @RequestBody CustomerUpdateRequest request) {
        Response<CustomerDto> response = new Response<>(ResponseStatus.of(ResponseCode.OK_NOTIFY, "Customer updated successfully"));
        CustomerDto dto = customerService.updateCustomer(id, request);
        response.setData(dto);
        return response;
    }

    @GetMapping("/{customerCode}")
    public Response<CustomerDto> getCustomerByCode(@PathVariable String customerCode) {
        Response<CustomerDto> response = new Response<>(ResponseStatus.of(ResponseCode.OK, "Customer retrieved successfully"));
        response.setData(customerService.getCustomerByCode(customerCode));
        return response;
    }

    @DeleteMapping("{id}")
    public Response<Void> softDeleteCustomer(@PathVariable Long id) {
        customerService.softDeleteCustomer(id);
        Response<Void> response = new Response<>(ResponseStatus.of(ResponseCode.OK, "Customer deleted successfully"));
        return response;
    }

    @PutMapping("{code}/contacts")
    public Response<Void> putMethodName(@PathVariable String code, @RequestBody CustomerContactDto body) {
        customerService.updateCustomerContact(code, body);
        return new Response<>(ResponseStatus.of(ResponseCode.OK_NOTIFY,
                body.getId() == null ? "Contact created successfully" : "Contact updated successfully"));
    }

    @DeleteMapping("{code}/contacts/{contactId}")
    public Response<Void> deleteCustomerContact(@PathVariable String code, @PathVariable Long contactId) {
        customerService.deleteCustomerContact(code, contactId);
        return new Response<>(ResponseStatus.of(ResponseCode.OK_NOTIFY, "Contact deleted successfully"));
    }
}
