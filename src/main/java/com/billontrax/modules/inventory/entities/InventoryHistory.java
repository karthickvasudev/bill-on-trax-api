package com.billontrax.modules.inventory.entities;

import com.billontrax.common.entities.TimestampedWithUser;
import com.billontrax.modules.inventory.enums.InventoryHistoryType;
import com.billontrax.modules.product.entities.Product;
import com.billontrax.modules.store.entities.Store;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * InventoryHistory entity records stock movements for products.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "inventory_history")
public class InventoryHistory extends TimestampedWithUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Product FK */
    @Column(name = "product_id", nullable = false)
    @NotNull
    private Long productId;

    /** Optional warehouse/store FK */
    @Column(name = "warehouse_id")
    private Long warehouseId;

    /** Type of history entry */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private InventoryHistoryType type;

    /** Quantity changed (positive integer) */
    @Column(nullable = false)
    @NotNull
    private Integer quantity;

    /** Optional reason / note */
    @Lob
    private String reason;

    /** Optional external reference id */
    @Column(length = 255)
    private String referenceId;

    // Relationships (read-only) -------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", insertable = false, updatable = false)
    private Store warehouse;
}

