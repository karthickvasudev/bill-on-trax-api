package com.billontrax.modules.customer.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.billontrax.modules.customer.dtos.CustomerContactDto;
import com.billontrax.modules.customer.dtos.CustomerCreateRequest;
import com.billontrax.modules.customer.dtos.CustomerDto;
import com.billontrax.modules.customer.dtos.CustomerUpdateRequest;
import com.billontrax.modules.customer.entities.Customer;
import com.billontrax.modules.customer.entities.CustomerContact;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(source = "contacts", target = "contacts")
    CustomerDto toDto(Customer entity);

    Customer toEntity(CustomerCreateRequest request);

    void updateEntityFromDto(CustomerUpdateRequest request, @MappingTarget Customer entity);

    void updateContactFromDto(CustomerContactDto dto, @MappingTarget CustomerContact entity);
}
