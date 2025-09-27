package com.billontrax.modules.orders.dtos;

import com.billontrax.modules.orders.enums.OrderStatus;
import com.billontrax.modules.orders.enums.OrderType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderRequestDto {
    @NotNull
    private Long customerId;

    @NotNull
    private OrderType orderType;

    @NotNull
    private OrderStatus status;

    private BigDecimal discountAmount;
    private BigDecimal taxAmount;

    @NotNull
    private Long createdBy;

    private String notes;

    @Valid
    @Size(min = 1)
    private List<OrderItemDto> orderItems;

    @Valid
    private List<OrderPaymentDto> orderPayments;
}

