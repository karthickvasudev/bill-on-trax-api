package com.billontrax.modules.inventory.entities;

import com.billontrax.common.entities.TimestampedWithUser;
import com.billontrax.modules.product.entities.Product;
import com.billontrax.modules.warehouse.entities.Warehouse;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Inventory entity representing stock information for a product (optionally per warehouse).
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "inventory", uniqueConstraints = {@UniqueConstraint(columnNames = {"inventory_id"})})
public class Inventory extends TimestampedWithUser {
    /** Auto-generated primary key */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Unique inventory identifier (human readable / external) */
    @Column(name = "inventory_id", length = 255, nullable = false, unique = true)
    @NotNull
    @Size(max = 255)
    private String inventoryId;

    /** FK column to product table - stored as id (used for queries) */
    @Column(name = "product_id", nullable = false)
    @NotNull
    private Long productId;

    /** Optional FK column to warehouse/store */
    @Column(name = "warehouse_id")
    private Long warehouseId;

    /** Current stock quantity */
    @Column(name = "stock_quantity", nullable = false)
    @NotNull
    private Integer stockQuantity;

    /** Low stock alert threshold */
    @Column(name = "low_stock_alert")
    private Integer lowStockAlert;

    /** Reorder level */
    @Column(name = "reorder_level")
    private Integer reorderLevel;

    /** Soft delete flag - when true the record is considered deleted */
    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    // Relationships (read-only views on the FK columns) --------------------------------

    /** Product relationship (read-only, productId is the owning column) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    /** Warehouse / Store relationship (read-only) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", insertable = false, updatable = false)
    private Warehouse warehouse;

}
