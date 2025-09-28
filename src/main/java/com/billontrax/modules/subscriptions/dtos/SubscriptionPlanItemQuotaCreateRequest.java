package com.billontrax.modules.subscriptions.dtos;

import com.billontrax.modules.subscriptions.enums.ItemType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating subscription plan item quotas
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionPlanItemQuotaCreateRequest {

    @NotNull(message = "Item type is required")
    private ItemType itemType;

    @NotNull(message = "Item ID is required")
    private Long itemId;

    @NotNull(message = "Allowed quantity is required")
    @Min(value = 0, message = "Allowed quantity must be non-negative")
    private Integer allowedQuantity;
}
