package com.billontrax.modules.product.dtos;

import com.billontrax.modules.product.enums.ProductType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Request body for creating a product.
 */
@Data
public class ProductCreateRequest {
    @NotBlank
    private String name; // Product name

    @NotBlank
    private String sku; // Unique SKU

    @NotNull
    private ProductType type; // Product type

    @NotNull
    @Digits(integer = 15, fraction = 2)
    private BigDecimal price; // Selling price

    @NotNull
    private Boolean taxInclusive; // Price includes tax?

    @NotNull
    @Digits(integer = 5, fraction = 2)
    private BigDecimal taxRate; // Tax rate percentage

    private Boolean isActive = true; // Active state

    private String description; // Long description
    private String barcode; // Barcode / EAN / UPC
    private String unit; // Unit of measure
    private Long categoryId; // Category reference
    private String brand; // Brand name
    private String hsnCode; // HSN/SAC code
    private Map<String, Object> customFields; // Custom JSON fields
    private List<String> tags; // Product tags

    // Inventory fields (required when type = PHYSICAL)
    private Integer stockQuantity; // Stock qty
    private BigDecimal costPrice; // Cost price
    private Integer lowStockAlert; // Low stock threshold
    private Long warehouseId; // Warehouse id
    private Integer reorderLevel; // Reorder level
}

