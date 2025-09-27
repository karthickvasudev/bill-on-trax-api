package com.billontrax.modules.orders.entities;

import com.billontrax.modules.orders.enums.ItemType;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @NotNull
    @Enumerated(EnumType.STRING)
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

    @NotNull
    @Column(precision = 19, scale = 2)
    private BigDecimal totalPrice;

    @Column(precision = 5, scale = 2)
    private BigDecimal taxPercentage;

    @NotNull
    @Column(precision = 19, scale = 2)
    private BigDecimal finalPrice;
}

