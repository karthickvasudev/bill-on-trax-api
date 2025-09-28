package com.billontrax.modules.payments.dtos;

import com.billontrax.modules.payments.enums.PaymentMode;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentDto {
    private Long id;
    private String paymentNumber;
    private Long customerId;
    private Long orderId;
    private BigDecimal amountPaid;
    private BigDecimal discountGiven;
    private PaymentMode paymentMode;
    private String referenceNumber;
    private String paymentDate; // ISO string via MapStruct default conversion
    private String notes;
    private Boolean isDeleted;
    private Long createdBy;
    private Long updatedBy;
    private String createdTime;
    private String updatedTime;
}

