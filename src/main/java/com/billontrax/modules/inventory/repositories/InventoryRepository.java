package com.billontrax.modules.inventory.repositories;

import com.billontrax.modules.inventory.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
}
