package com.billontrax.modules.orders.dtos;

import com.billontrax.modules.orders.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderStatusUpdateDto {
    @NotNull
    private OrderStatus status;
}
