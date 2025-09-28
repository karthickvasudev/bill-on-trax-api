package com.billontrax.modules.subscriptions.dtos;

import com.billontrax.modules.subscriptions.enums.SubscriptionStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for updating customer subscription status
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSubscriptionStatusUpdateRequest {

    @NotNull(message = "Status is required")
    private SubscriptionStatus status;
}
