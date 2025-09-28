package com.billontrax.modules.subscriptions.dtos;

import com.billontrax.modules.subscriptions.enums.PlanType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for updating subscription plans
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionPlanUpdateRequest {

    @Size(max = 255, message = "Plan name must not exceed 255 characters")
    private String name;

    @DecimalMin(value = "0.0", message = "Price must be non-negative")
    @Digits(integer = 15, fraction = 2, message = "Price format is invalid")
    private BigDecimal price;

    private PlanType planType;

    @Min(value = 1, message = "Validity days must be at least 1")
    private Integer validityDays;

    private String description;
}
