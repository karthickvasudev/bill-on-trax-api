package com.billontrax.modules.orders.entities;

import com.billontrax.modules.orders.enums.PaymentMode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "order_payments")
public class OrderPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @NotNull
    @Column(precision = 19, scale = 2)
    private BigDecimal amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;

    private String referenceNo;

    @NotNull
    private Date paidOn;

    @Lob
    private String notes;
}

