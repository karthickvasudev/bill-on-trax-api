package com.billontrax.modules.orders.services;

import com.billontrax.modules.orders.dtos.OrderRequestDto;
import com.billontrax.modules.orders.dtos.OrderResponseDto;
import com.billontrax.modules.orders.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderResponseDto createOrder(OrderRequestDto dto);
    OrderResponseDto updateOrder(Long id, OrderRequestDto dto);
    OrderResponseDto getOrderById(Long id);
    Page<OrderResponseDto> listOrders(Pageable pageable);
    OrderResponseDto updateOrderStatus(Long id, OrderStatus status);
    void softDelete(Long id);
}

