package com.billontrax.modules.subscriptions.dtos;

import com.billontrax.modules.subscriptions.enums.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * DTO for customer subscription response
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSubscriptionDto {
    private Long id;
    private Long customerId;
    private SubscriptionPlanDto subscriptionPlan;
    private LocalDate startDate;
    private LocalDate endDate;
    private SubscriptionStatus status;
    private List<SubscriptionItemUsageDto> itemUsages;
    private Date createdTime;
    private Long createdBy;
    private Date updatedTime;
    private Long updatedBy;
}
