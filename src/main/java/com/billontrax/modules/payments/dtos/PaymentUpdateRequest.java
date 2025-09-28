package com.billontrax.modules.payments.dtos;

import com.billontrax.modules.payments.enums.PaymentMode;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentUpdateRequest {
    private Long orderId; // can be set if initially advance payment
    @Positive
    private BigDecimal amountPaid;
    @PositiveOrZero
    private BigDecimal discountGiven;
    private PaymentMode paymentMode;
    private String referenceNumber;
    private LocalDateTime paymentDate;
    private String notes;
}
