package com.billontrax.modules.inventory.dtos;

import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Request DTO to update an Inventory record. Fields left null will be ignored.
 */
@Data
public class InventoryUpdateRequest {
    @Size(max = 255)
    private String inventoryId;

    private Long productId;

    private Long warehouseId;

    private Integer stockQuantity;

    private Integer lowStockAlert;

    private Integer reorderLevel;
}

