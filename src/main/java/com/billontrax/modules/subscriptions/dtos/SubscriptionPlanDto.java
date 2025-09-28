package com.billontrax.modules.subscriptions.dtos;

import com.billontrax.modules.subscriptions.enums.PlanType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * DTO for subscription plan response
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionPlanDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private PlanType planType;
    private Integer validityDays;
    private String description;
    private List<SubscriptionPlanItemQuotaDto> itemQuotas;
    private Date createdTime;
    private Long createdBy;
    private Date updatedTime;
    private Long updatedBy;
}
