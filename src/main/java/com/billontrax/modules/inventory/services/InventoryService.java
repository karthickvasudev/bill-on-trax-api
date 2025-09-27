package com.billontrax.modules.inventory.services;

import com.billontrax.modules.inventory.dtos.InventoryCreateRequest;
import com.billontrax.modules.inventory.dtos.InventoryDto;
import com.billontrax.modules.inventory.dtos.InventoryUpdateRequest;
import com.billontrax.modules.inventory.dtos.StockOperationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryService {
    InventoryDto create(InventoryCreateRequest request);
    InventoryDto get(Long id);
    InventoryDto update(Long id, InventoryUpdateRequest request);
    void delete(Long id);

    Page<InventoryDto> list(Long productId, Long warehouseId, Pageable pageable);

    // Stock operations that update inventory and create history
    InventoryDto increaseStock(Long inventoryId, StockOperationRequest req);
    InventoryDto decreaseStock(Long inventoryId, StockOperationRequest req);
    InventoryDto adjustStock(Long inventoryId, StockOperationRequest req);
}
