package com.billontrax.modules.subscriptions.dtos;

import com.billontrax.modules.subscriptions.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DTO for subscription plan item quota response
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionPlanItemQuotaDto {
    private Long id;
    private Long planId;
    private ItemType itemType;
    private Long itemId;
    private Integer allowedQuantity;
    private Date createdTime;
    private Long createdBy;
    private Date updatedTime;
    private Long updatedBy;
}
