package com.billontrax.modules.customer.mappers;

import org.mapstruct.*;

import com.billontrax.modules.customer.dtos.CustomerContactDto;
import com.billontrax.modules.customer.dtos.CustomerCreateRequest;
import com.billontrax.modules.customer.dtos.CustomerDto;
import com.billontrax.modules.customer.dtos.CustomerUpdateRequest;
import com.billontrax.modules.customer.entities.Customer;
import com.billontrax.modules.customer.entities.CustomerContact;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "contacts", source = "contacts")
    CustomerDto toDto(Customer entity);

    Customer toEntity(CustomerCreateRequest request);

    void updateEntityFromDto(CustomerUpdateRequest request, @MappingTarget Customer entity);

    void updateContactFromDto(CustomerContactDto dto, @MappingTarget CustomerContact entity);
}
