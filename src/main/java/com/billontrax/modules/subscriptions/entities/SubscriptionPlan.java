package com.billontrax.modules.subscriptions.entities;

import com.billontrax.common.entities.TimestampedWithUser;
import com.billontrax.modules.subscriptions.enums.PlanType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Subscription Plan entity representing different subscription tiers
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "subscription_plan", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class SubscriptionPlan extends TimestampedWithUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Plan name is required")
    @Size(max = 255)
    @Column(name = "name", unique = true)
    private String name;

    @NotNull(message = "Price is required")
    @Digits(integer = 15, fraction = 2)
    @Column(name = "price")
    private BigDecimal price;

    @NotNull(message = "Plan type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "plan_type")
    private PlanType planType;

    @NotNull(message = "Validity days is required")
    @Min(value = 1, message = "Validity days must be at least 1")
    @Column(name = "validity_days")
    private Integer validityDays;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}
