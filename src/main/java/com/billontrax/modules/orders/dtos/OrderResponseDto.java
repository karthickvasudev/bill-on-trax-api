package com.billontrax.modules.orders.dtos;

import com.billontrax.modules.orders.enums.OrderStatus;
import com.billontrax.modules.orders.enums.OrderType;
import com.billontrax.modules.orders.enums.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderResponseDto {
    private Long id;
    private String orderNumber;
    private Long customerId;
    private OrderType orderType;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal taxAmount;
    private BigDecimal finalAmount;
    private PaymentStatus paymentStatus;
    private String notes;
    private Boolean isDeleted;
    private Long createdBy;
    private Date createdTime;
    private Long updatedBy;
    private Date updatedTime;
    private List<OrderItemDto> orderItems;
    private List<OrderPaymentDto> orderPayments;
}

