package com.billontrax.modules.payments.entities;

import com.billontrax.common.entities.TimestampedWithUser;
import com.billontrax.modules.payments.enums.PaymentMode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "payment")
public class Payment extends TimestampedWithUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_number", unique = true)
    private String paymentNumber;

    @NotNull
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "order_id")
    private Long orderId;

    @NotNull
    @Column(name = "amount_paid", precision = 19, scale = 2, nullable = false)
    private BigDecimal amountPaid;

    @Column(name = "discount_given", precision = 19, scale = 2)
    private BigDecimal discountGiven = BigDecimal.ZERO;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_mode", nullable = false, length = 50)
    private PaymentMode paymentMode;

    @Column(name = "reference_number")
    private String referenceNumber;

    @NotNull
    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    @Lob
    @Column(name = "notes")
    private String notes;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;
}

