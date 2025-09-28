package com.billontrax.modules.subscriptions.entities;

import com.billontrax.common.entities.TimestampedWithUser;
import com.billontrax.modules.subscriptions.enums.ItemType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Subscription Plan Item Quota entity for managing item limits per plan
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "subscription_plan_item_quota",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"plan_id", "item_type", "item_id"})})
public class SubscriptionPlanItemQuota extends TimestampedWithUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Plan is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private SubscriptionPlan subscriptionPlan;

    @NotNull(message = "Item type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "item_type")
    private ItemType itemType;

    @NotNull(message = "Item ID is required")
    @Column(name = "item_id")
    private Long itemId;

    @NotNull(message = "Allowed quantity is required")
    @Min(value = 0, message = "Allowed quantity must be non-negative")
    @Column(name = "allowed_quantity")
    private Integer allowedQuantity;
}
