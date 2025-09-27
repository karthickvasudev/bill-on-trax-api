package com.billontrax.modules.product.entities;

import com.billontrax.common.convertors.MapStringObjectConvertor;
import com.billontrax.common.entities.TimestampedWithUser;
import com.billontrax.modules.product.enums.ProductType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Product entity representing an item or service offered by the business.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "product", uniqueConstraints = {@UniqueConstraint(columnNames = {"sku"})})
public class Product extends TimestampedWithUser {
    /** Auto-generated primary key */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Human readable product name */
    @NotBlank
    @Size(max = 255)
    private String name;

    /** Unique SKU (Stock Keeping Unit) identifier */
    @NotBlank
    @Size(max = 255)
    private String sku;

    /** Type of product controlling inventory logic */
    @NotNull
    @Enumerated(EnumType.STRING)
    private ProductType type;

    /** Selling price (inclusive or exclusive of tax based on taxInclusive flag) */
    @NotNull
    @Digits(integer = 15, fraction = 2)
    private BigDecimal price;

    /** Flag indicating if price already includes tax */
    @NotNull
    private Boolean taxInclusive;

    /** Active flag (soft delete handled by toggling this) */
    @NotNull
    private Boolean isActive = true;

    /** Long form description */
    @Lob
    private String description;

    /** Barcode / EAN / UPC reference */
    private String barcode;

    /** Unit of measure (e.g., pcs, kg, hr) */
    private String unit;

    /** Related category id (FK to category table) */
    private Long categoryId;

    /** Brand name */
    private String brand;

    /** HSN / SAC code for taxation */
    private String hsnCode;

    /** Arbitrary custom fields stored as JSON */
    @Convert(converter = MapStringObjectConvertor.class)
    @Column(columnDefinition = "text")
    private Map<String, Object> customFields;

    /** List of tags for search / grouping */
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "product_tags", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "tag", length = 100)
    private List<String> tags;

    // Inventory fields (only applicable when type = PHYSICAL) -----------------

    /** Current stock quantity */
    private Integer stockQuantity; // validated in service when PHYSICAL

    /** Cost (purchase) price per unit */
    @Digits(integer = 15, fraction = 2)
    private BigDecimal costPrice;  // required in service when PHYSICAL

    /** Low stock alert threshold */
    private Integer lowStockAlert;

    /** Warehouse identifier (future relation) */
    private Long warehouseId;

    /** Reorder level for replenishment planning */
    private Integer reorderLevel;

    // Tax fields --------------------------------------------------------------

    /** Applicable tax rate (percentage) */
    @NotNull
    @Digits(integer = 5, fraction = 2)
    private BigDecimal taxRate;
}

