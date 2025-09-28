package com.billontrax.modules.subscriptions.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO for creating customer subscriptions
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSubscriptionCreateRequest {

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Plan ID is required")
    private Long planId;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;
}
