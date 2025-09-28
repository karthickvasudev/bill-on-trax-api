package com.billontrax.modules.subscriptions.entities;

import com.billontrax.common.entities.TimestampedWithUser;
import com.billontrax.modules.subscriptions.enums.ItemType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Subscription Item Usage entity for tracking usage per customer per item
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "subscription_item_usage")
public class SubscriptionItemUsage extends TimestampedWithUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Customer subscription is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_subscription_id", nullable = false)
    private CustomerSubscription customerSubscription;

    @NotNull(message = "Item type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "item_type")
    private ItemType itemType;

    @NotNull(message = "Item ID is required")
    @Column(name = "item_id")
    private Long itemId;

    @NotNull(message = "Used quantity is required")
    @Min(value = 0, message = "Used quantity must be non-negative")
    @Column(name = "used_quantity")
    private Integer usedQuantity = 0;
}
