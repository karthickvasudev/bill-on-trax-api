package com.billontrax.modules.inventory.dtos;

import com.billontrax.modules.inventory.enums.InventoryHistoryType;
import lombok.Data;

import java.util.Date;

/**
 * Response DTO for inventory history records.
 */
@Data
public class InventoryHistoryDto {
    private Long id;
    private Long productId;
    private Long warehouseId;
    private InventoryHistoryType type;
    private Integer quantity;
    private String reason;
    private String referenceId;
    private Date createdTime;
    private Long createdBy;
}

