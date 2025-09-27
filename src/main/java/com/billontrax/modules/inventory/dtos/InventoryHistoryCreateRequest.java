package com.billontrax.modules.inventory.dtos;

import com.billontrax.modules.inventory.enums.InventoryHistoryType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Request DTO to create an inventory history record (manual or via stock ops).
 */
@Data
public class InventoryHistoryCreateRequest {
    @NotNull
    private Long productId;

    private Long warehouseId;

    @NotNull
    private InventoryHistoryType type;

    @NotNull
    private Integer quantity;

    private String reason;

    @Size(max = 255)
    private String referenceId;
}

