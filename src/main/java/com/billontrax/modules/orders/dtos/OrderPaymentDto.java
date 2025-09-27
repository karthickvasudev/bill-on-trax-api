package com.billontrax.modules.orders.dtos;

import com.billontrax.modules.orders.enums.PaymentMode;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderPaymentDto {
    private Long id;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private PaymentMode paymentMode;

    private String referenceNo;

    @NotNull
    private Date paidOn;

    private String notes;
}

