package com.billontrax.modules.subscriptions.dtos;

import com.billontrax.modules.subscriptions.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DTO for subscription item usage response
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionItemUsageDto {
    private Long id;
    private Long customerSubscriptionId;
    private ItemType itemType;
    private Long itemId;
    private Integer usedQuantity;
    private Integer allowedQuantity; // From plan quota
    private Integer remainingQuantity; // Calculated field
    private Date createdTime;
    private Long createdBy;
    private Date updatedTime;
    private Long updatedBy;
}
