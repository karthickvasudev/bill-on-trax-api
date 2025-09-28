package com.billontrax.modules.payments.mappers;

import org.mapstruct.*;
import com.billontrax.modules.payments.dtos.PaymentCreateRequest;
import com.billontrax.modules.payments.dtos.PaymentDto;
import com.billontrax.modules.payments.dtos.PaymentUpdateRequest;
import com.billontrax.modules.payments.entities.Payment;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment toEntity(PaymentCreateRequest request);

    @Mapping(target = "paymentDate", expression = "java(entity.getPaymentDate() == null ? null : entity.getPaymentDate().toString())")
    @Mapping(target = "createdTime", expression = "java(entity.getCreatedTime() == null ? null : entity.getCreatedTime().toString())")
    @Mapping(target = "updatedTime", expression = "java(entity.getUpdatedTime() == null ? null : entity.getUpdatedTime().toString())")
    PaymentDto toDto(Payment entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(PaymentUpdateRequest request, @MappingTarget Payment entity);

    default String map(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.toString();
    }
}
