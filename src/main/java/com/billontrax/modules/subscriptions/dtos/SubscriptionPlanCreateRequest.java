package com.billontrax.modules.subscriptions.dtos;

import com.billontrax.modules.subscriptions.enums.PlanType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for creating subscription plans
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionPlanCreateRequest {

    @NotBlank(message = "Plan name is required")
    @Size(max = 255, message = "Plan name must not exceed 255 characters")
    private String name;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price must be non-negative")
    @Digits(integer = 15, fraction = 2, message = "Price format is invalid")
    private BigDecimal price;

    @NotNull(message = "Plan type is required")
    private PlanType planType;

    @NotNull(message = "Validity days is required")
    @Min(value = 1, message = "Validity days must be at least 1")
    private Integer validityDays;

    private String description;
}
