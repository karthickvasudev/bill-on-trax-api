package com.billontrax.modules.payments.dtos;

import com.billontrax.modules.payments.enums.PaymentMode;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentCreateRequest {
    @NotNull
    private Long customerId;
    private Long orderId; // nullable
    @NotNull
    @Positive
    private BigDecimal amountPaid;
    @PositiveOrZero
    private BigDecimal discountGiven; // optional
    @NotNull
    private PaymentMode paymentMode;
    private String referenceNumber; // optional
    private LocalDateTime paymentDate; // if null -> now
    private String notes; // optional
}
