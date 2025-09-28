package com.billontrax.modules.warehouse.services;

import com.billontrax.modules.warehouse.dtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WarehouseService {
    WarehouseDetailDto create(WarehouseCreateRequest req);
    WarehouseDetailDto get(Long id);
    Page<WarehouseSummaryDto> list(String name, Boolean isActive, Pageable pageable);
    WarehouseDetailDto update(Long id, WarehouseUpdateRequest req);
    void delete(Long id); // soft delete (isActive=false)
}

