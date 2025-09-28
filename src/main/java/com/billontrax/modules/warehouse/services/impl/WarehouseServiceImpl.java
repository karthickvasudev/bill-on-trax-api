package com.billontrax.modules.warehouse.services.impl;

import com.billontrax.common.exceptions.ErrorMessageException;
import com.billontrax.modules.inventory.repositories.InventoryRepository;
import com.billontrax.modules.warehouse.dtos.*;
import com.billontrax.modules.warehouse.entities.Warehouse;
import com.billontrax.modules.warehouse.mappers.WarehouseMapper;
import com.billontrax.modules.warehouse.repositories.WarehouseRepository;
import com.billontrax.modules.warehouse.services.WarehouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final InventoryRepository inventoryRepository;
    private final WarehouseMapper mapper;

    @Override
    @Transactional
    public WarehouseDetailDto create(WarehouseCreateRequest req) {
        log.info("Creating warehouse: {}", req);
        warehouseRepository.findByNameIgnoreCase(req.getName()).ifPresent(w -> { throw new ErrorMessageException("Warehouse with this name already exists"); });
        try {
            Warehouse entity = mapper.toEntity(req);
            if (entity.getIsActive() == null) entity.setIsActive(true);
            Warehouse saved = warehouseRepository.save(entity);
            return enrichDetail(saved);
        } catch (DataIntegrityViolationException e) {
            throw new ErrorMessageException("Warehouse with this name already exists");
        }
    }

    @Override
    public WarehouseDetailDto get(Long id) {
        Warehouse w = warehouseRepository.findById(id).orElseThrow(() -> new ErrorMessageException("Warehouse not found"));
        return enrichDetail(w);
    }

    @Override
    public Page<WarehouseSummaryDto> list(String name, Boolean isActive, Pageable pageable) {
        Page<Warehouse> page;
        if (name != null && !name.isBlank() && isActive != null) {
            page = warehouseRepository.findByNameContainingIgnoreCaseAndIsActive(name, isActive, pageable);
        } else if (name != null && !name.isBlank()) {
            page = warehouseRepository.findByNameContainingIgnoreCase(name, pageable);
        } else if (isActive != null) {
            page = warehouseRepository.findByIsActive(isActive, pageable);
        } else {
            page = warehouseRepository.findAll(pageable);
        }
        List<WarehouseSummaryDto> summaries = page.getContent().stream().map(this::enrichSummary).collect(Collectors.toList());
        return new PageImpl<>(summaries, pageable, page.getTotalElements());
    }

    @Override
    @Transactional
    public WarehouseDetailDto update(Long id, WarehouseUpdateRequest req) {
        Warehouse w = warehouseRepository.findById(id).orElseThrow(() -> new ErrorMessageException("Warehouse not found"));
        if (req.getName() != null && !req.getName().equalsIgnoreCase(w.getName())) {
            warehouseRepository.findByNameIgnoreCase(req.getName()).ifPresent(existing -> { throw new ErrorMessageException("Warehouse with this name already exists"); });
        }
        mapper.updateEntityFromDto(req, w);
        Warehouse saved = warehouseRepository.save(w);
        return enrichDetail(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Warehouse w = warehouseRepository.findById(id).orElseThrow(() -> new ErrorMessageException("Warehouse not found"));
        w.setIsActive(false); // soft delete
        warehouseRepository.save(w);
    }

    private WarehouseSummaryDto enrichSummary(Warehouse entity) {
        WarehouseSummaryDto dto = new WarehouseSummaryDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setIsActive(entity.getIsActive());
        Long productCount = inventoryRepository.countDistinctProducts(entity.getId());
        Integer totalStock = inventoryRepository.sumStock(entity.getId());
        dto.setProductCount(productCount == null ? 0 : productCount);
        dto.setTotalStockQuantity(totalStock == null ? 0 : totalStock);
        // Include product-wise breakdown as required
        dto.setProductStocks(inventoryRepository.productStockBreakdown(entity.getId())
                .stream()
                .map(p -> {
                    WarehouseProductStockDto ps = new WarehouseProductStockDto();
                    ps.setProductId(p.getProductId());
                    ps.setProductName(p.getProductName());
                    ps.setStockQuantity(p.getStockQuantity());
                    return ps;
                }).collect(java.util.stream.Collectors.toList()));
        return dto;
    }

    private WarehouseDetailDto enrichDetail(Warehouse entity) {
        WarehouseDetailDto dto = mapper.toDetailDto(entity);
        Long productCount = inventoryRepository.countDistinctProducts(entity.getId());
        Integer totalStock = inventoryRepository.sumStock(entity.getId());
        dto.setProductCount(productCount == null ? 0 : productCount);
        dto.setTotalStockQuantity(totalStock == null ? 0 : totalStock);
        List<WarehouseProductStockDto> productStocks = inventoryRepository.productStockBreakdown(entity.getId())
                .stream()
                .map(p -> {
                    WarehouseProductStockDto ps = new WarehouseProductStockDto();
                    ps.setProductId(p.getProductId());
                    ps.setProductName(p.getProductName());
                    ps.setStockQuantity(p.getStockQuantity());
                    return ps;
                }).collect(Collectors.toList());
        dto.setProductStocks(productStocks);
        return dto;
    }
}
