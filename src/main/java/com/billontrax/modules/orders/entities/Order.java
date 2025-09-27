package com.billontrax.modules.orders.entities;

import com.billontrax.common.entities.TimestampedWithUser;
import com.billontrax.modules.orders.enums.OrderStatus;
import com.billontrax.modules.orders.enums.OrderType;
import com.billontrax.modules.orders.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "orders", uniqueConstraints = {@UniqueConstraint(columnNames = {"order_number"})})
public class Order extends TimestampedWithUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "order_number", nullable = false, unique = true)
    private String orderNumber;

    @NotNull
    private Long customerId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @NotNull
    @Column(precision = 19, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(precision = 19, scale = 2)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(precision = 19, scale = 2)
    private BigDecimal taxAmount = BigDecimal.ZERO;

    @NotNull
    @Column(precision = 19, scale = 2)
    private BigDecimal finalAmount = BigDecimal.ZERO;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Lob
    private String notes;

    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderPayment> orderPayments = new ArrayList<>();
}

