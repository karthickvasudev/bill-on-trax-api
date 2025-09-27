package com.billontrax.modules.product.dtos;

import com.billontrax.modules.product.enums.ProductType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Product DTO for API responses.
 */
@Data
public class ProductDto {
    private Long id; // Product id
    private String name; // Product name
    private String sku; // SKU
    private ProductType type; // Type
    private BigDecimal price; // Selling price
    private Boolean taxInclusive; // Inclusive tax flag
    private Boolean isActive; // Active flag
    private String description; // Description
    private String barcode; // Barcode
    private String unit; // Unit of measure
    private Long categoryId; // Category id
    private String brand; // Brand name
    private String hsnCode; // HSN code
    private Map<String, Object> customFields; // Custom fields
    private List<String> tags; // Tags
    private Integer stockQuantity; // Stock
    private BigDecimal costPrice; // Cost price
    private Integer lowStockAlert; // Low stock threshold
    private Long warehouseId; // Warehouse id
    private Integer reorderLevel; // Reorder level
    private BigDecimal taxRate; // Tax rate
    private Date createdTime; // Created timestamp
    private Long createdBy; // Creator user id
    private Date updatedTime; // Updated timestamp
    private Long updatedBy; // Updater user id
}

