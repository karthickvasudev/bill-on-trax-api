package com.billontrax.modules.orders.dtos;

import com.billontrax.modules.orders.enums.ItemType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Long id;

    @NotNull
    private ItemType itemType;

    @NotNull
    private Long itemId;

    private String description;

    @NotNull
    @Min(1)
    private Integer quantity;

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal unitPrice;

    private BigDecimal totalPrice;
    private BigDecimal taxPercentage;
    private BigDecimal finalPrice;
}

