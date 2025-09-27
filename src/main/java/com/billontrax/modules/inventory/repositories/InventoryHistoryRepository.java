package com.billontrax.modules.inventory.repositories;

import com.billontrax.modules.inventory.entities.InventoryHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface InventoryHistoryRepository extends JpaRepository<InventoryHistory, Long> {
    List<InventoryHistory> findByProductId(Long productId);
    List<InventoryHistory> findByWarehouseId(Long warehouseId);

    Page<InventoryHistory> findByProductId(Long productId, Pageable pageable);
    Page<InventoryHistory> findByWarehouseId(Long warehouseId, Pageable pageable);

    Page<InventoryHistory> findByCreatedTimeBetween(Date from, Date to, Pageable pageable);
    Page<InventoryHistory> findByProductIdAndCreatedTimeBetween(Long productId, Date from, Date to, Pageable pageable);
    Page<InventoryHistory> findByWarehouseIdAndCreatedTimeBetween(Long warehouseId, Date from, Date to, Pageable pageable);
}

