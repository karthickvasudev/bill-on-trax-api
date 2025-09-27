package com.billontrax.modules.orders.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.billontrax.modules.orders.dtos.OrderItemDto;
import com.billontrax.modules.orders.dtos.OrderPaymentDto;
import com.billontrax.modules.orders.dtos.OrderRequestDto;
import com.billontrax.modules.orders.dtos.OrderResponseDto;
import com.billontrax.modules.orders.entities.Order;
import com.billontrax.modules.orders.entities.OrderItem;
import com.billontrax.modules.orders.entities.OrderPayment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderResponseDto toDto(Order entity);

    Order toEntity(OrderRequestDto dto);

    OrderItemDto toItemDto(OrderItem item);
    OrderItem toItemEntity(OrderItemDto dto);

    OrderPaymentDto toPaymentDto(OrderPayment payment);
    OrderPayment toPaymentEntity(OrderPaymentDto dto);

    void updateEntityFromDto(OrderRequestDto dto, @MappingTarget Order entity);
}

