package com.billontrax.modules.inventory.services;

import com.billontrax.modules.inventory.dtos.InventoryHistoryCreateRequest;
import com.billontrax.modules.inventory.dtos.InventoryHistoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface InventoryHistoryService {
    InventoryHistoryDto create(InventoryHistoryCreateRequest request);

    Page<InventoryHistoryDto> list(Long productId, Long warehouseId, Date from, Date to, Pageable pageable);
}

