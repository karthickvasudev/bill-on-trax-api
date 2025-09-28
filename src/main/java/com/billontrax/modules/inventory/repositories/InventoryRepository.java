package com.billontrax.modules.inventory.repositories;

import com.billontrax.modules.inventory.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByIdAndDeletedFalse(Long id);
    Optional<Inventory> findByInventoryIdAndDeletedFalse(String inventoryId);
    List<Inventory> findByProductIdAndDeletedFalse(Long productId);
    List<Inventory> findByWarehouseIdAndDeletedFalse(Long warehouseId);

    // Single inventory for a product in a warehouse (warehouseId can be null)
    Optional<Inventory> findByProductIdAndWarehouseIdAndDeletedFalse(Long productId, Long warehouseId);

    // Pageable variants for listing with pagination - only non-deleted
    Page<Inventory> findByDeletedFalse(Pageable pageable);
    Page<Inventory> findByProductIdAndDeletedFalse(Long productId, Pageable pageable);
    Page<Inventory> findByWarehouseIdAndDeletedFalse(Long warehouseId, Pageable pageable);
    Page<Inventory> findByProductIdAndWarehouseIdAndDeletedFalse(Long productId, Long warehouseId, Pageable pageable);

    // Aggregations for warehouse summaries
    @Query("select count(distinct i.productId) from Inventory i where i.deleted=false and i.warehouseId = :wid")
    Long countDistinctProducts(@Param("wid") Long warehouseId);

    @Query("select coalesce(sum(i.stockQuantity),0) from Inventory i where i.deleted=false and i.warehouseId = :wid")
    Integer sumStock(@Param("wid") Long warehouseId);

    interface WarehouseProductStockProjection {
        Long getProductId();
        String getProductName();
        Integer getStockQuantity();
    }

    @Query("select i.productId as productId, p.name as productName, sum(i.stockQuantity) as stockQuantity " +
            "from Inventory i join com.billontrax.modules.product.entities.Product p on p.id = i.productId " +
            "where i.deleted=false and i.warehouseId = :wid group by i.productId, p.name")
    List<WarehouseProductStockProjection> productStockBreakdown(@Param("wid") Long warehouseId);
}
