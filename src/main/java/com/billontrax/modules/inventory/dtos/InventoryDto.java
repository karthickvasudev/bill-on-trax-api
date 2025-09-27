package com.billontrax.modules.inventory.dtos;

import lombok.Data;

import java.util.Date;

/**
 * Response DTO representing inventory data.
 */
@Data
public class InventoryDto {
    private Long id;
    private String inventoryId;
    private Long productId;
    private Long warehouseId;
    private Integer stockQuantity;
    private Integer lowStockAlert;
    private Integer reorderLevel;
    private Date createdTime;
    private Long createdBy;
    private Date updatedTime;
    private Long updatedBy;
}

