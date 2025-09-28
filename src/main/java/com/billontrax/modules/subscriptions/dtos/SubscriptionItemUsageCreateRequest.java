package com.billontrax.modules.subscriptions.dtos;

import com.billontrax.modules.subscriptions.enums.ItemType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating/updating subscription item usage
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionItemUsageCreateRequest {

    @NotNull(message = "Customer subscription ID is required")
    private Long customerSubscriptionId;

    @NotNull(message = "Item type is required")
    private ItemType itemType;

    @NotNull(message = "Item ID is required")
    private Long itemId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    private Boolean isIncrement = true; // true for increment, false for decrement
}
