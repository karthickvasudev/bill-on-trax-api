package com.billontrax.modules.inventory.dtos;

import com.billontrax.modules.inventory.enums.InventoryHistoryType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Request DTO for stock operations (increase/decrease/adjust) on an inventory record.
 */
@Data
public class StockOperationRequest {
    @NotNull
    private InventoryHistoryType type; // STOCK_IN, STOCK_OUT, ADJUSTMENT

    @NotNull
    private Integer quantity; // For ADJUSTMENT this is the absolute value; for IN/OUT it's delta

    private String reason;

    private String referenceId;
}

