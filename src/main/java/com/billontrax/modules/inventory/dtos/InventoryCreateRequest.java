package com.billontrax.modules.inventory.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Request DTO to create an Inventory record.
 */
@Data
public class InventoryCreateRequest {
    @NotNull
    @Size(max = 255)
    private String inventoryId;

    @NotNull
    private Long productId;

    private Long warehouseId;

    @NotNull
    private Integer stockQuantity;

    private Integer lowStockAlert;

    private Integer reorderLevel;
}

