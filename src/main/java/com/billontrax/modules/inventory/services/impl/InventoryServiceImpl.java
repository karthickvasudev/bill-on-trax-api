package com.billontrax.modules.inventory.services.impl;

import com.billontrax.common.exceptions.ErrorMessageException;
import com.billontrax.modules.inventory.dtos.InventoryCreateRequest;
import com.billontrax.modules.inventory.dtos.InventoryDto;
import com.billontrax.modules.inventory.dtos.InventoryUpdateRequest;
import com.billontrax.modules.inventory.dtos.StockOperationRequest;
import com.billontrax.modules.inventory.entities.Inventory;
import com.billontrax.modules.inventory.enums.InventoryHistoryType;
import com.billontrax.modules.inventory.mappers.InventoryMapper;
import com.billontrax.modules.inventory.repositories.InventoryRepository;
import com.billontrax.modules.inventory.services.InventoryHistoryService;
import com.billontrax.modules.inventory.services.InventoryService;
import com.billontrax.modules.warehouse.repositories.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository repository;
    private final InventoryMapper mapper;
    private final InventoryHistoryService historyService;
    private final WarehouseRepository warehouseRepository;

    @Override
    @Transactional
    public InventoryDto create(InventoryCreateRequest request) {
        log.info("Creating inventory: {}", request);
        // Validate warehouse active if provided
        if (request.getWarehouseId() != null) {
            warehouseRepository.findById(request.getWarehouseId())
                    .filter(w -> Boolean.TRUE.equals(w.getIsActive()))
                    .orElseThrow(() -> new ErrorMessageException("Inactive or invalid warehouse specified"));
        }
        try {
            Inventory entity = mapper.toEntity(request);
            Inventory saved = repository.save(entity);
            return mapper.toDto(saved);
        } catch (DataIntegrityViolationException e) {
            throw new ErrorMessageException("Inventory with same inventoryId already exists");
        }
    }

    @Override
    public InventoryDto get(Long id) {
        Inventory inv = repository.findByIdAndDeletedFalse(id).orElseThrow(() -> new ErrorMessageException("Inventory not found"));
        return mapper.toDto(inv);
    }

    @Override
    @Transactional
    public InventoryDto update(Long id, InventoryUpdateRequest request) {
        Inventory inv = repository.findByIdAndDeletedFalse(id).orElseThrow(() -> new ErrorMessageException("Inventory not found"));
        if (request.getWarehouseId() != null && !request.getWarehouseId().equals(inv.getWarehouseId())) {
            warehouseRepository.findById(request.getWarehouseId())
                    .filter(w -> Boolean.TRUE.equals(w.getIsActive()))
                    .orElseThrow(() -> new ErrorMessageException("Inactive or invalid warehouse specified"));
        }
        mapper.updateEntityFromDto(request, inv);
        Inventory saved = repository.save(inv);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Inventory inv = repository.findByIdAndDeletedFalse(id).orElseThrow(() -> new ErrorMessageException("Inventory not found"));
        inv.setDeleted(true);
        // save will trigger @PreUpdate to set updatedTime and updatedBy
        repository.save(inv);
    }

    @Override
    public Page<InventoryDto> list(Long productId, Long warehouseId, Pageable pageable) {
        Page<Inventory> page;
        if (productId != null && warehouseId != null) {
            page = repository.findByProductIdAndWarehouseIdAndDeletedFalse(productId, warehouseId, pageable);
        } else if (productId != null) {
            page = repository.findByProductIdAndDeletedFalse(productId, pageable);
        } else if (warehouseId != null) {
            page = repository.findByWarehouseIdAndDeletedFalse(warehouseId, pageable);
        } else {
            page = repository.findByDeletedFalse(pageable);
        }
        List<InventoryDto> dtos = page.getContent().stream().map(mapper::toDto).collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, page.getTotalElements());
    }

    private void recordHistory(Long productId, Long warehouseId, InventoryHistoryType type, Integer quantity, String reason, String referenceId) {
        // Build create request for history and call historyService
        com.billontrax.modules.inventory.dtos.InventoryHistoryCreateRequest req = new com.billontrax.modules.inventory.dtos.InventoryHistoryCreateRequest();
        req.setProductId(productId);
        req.setWarehouseId(warehouseId);
        req.setType(type);
        req.setQuantity(quantity);
        req.setReason(reason);
        req.setReferenceId(referenceId);
        historyService.create(req);
    }

    @Override
    @Transactional
    public InventoryDto increaseStock(Long inventoryId, StockOperationRequest req) {
        Inventory inv = repository.findByIdAndDeletedFalse(inventoryId).orElseThrow(() -> new ErrorMessageException("Inventory not found"));
        if (req.getQuantity() == null || req.getQuantity() <= 0) throw new ErrorMessageException("quantity must be a positive integer");
        inv.setStockQuantity((inv.getStockQuantity() == null ? 0 : inv.getStockQuantity()) + req.getQuantity());
        Inventory saved = repository.save(inv);
        recordHistory(inv.getProductId(), inv.getWarehouseId(), InventoryHistoryType.STOCK_IN, req.getQuantity(), req.getReason(), req.getReferenceId());
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public InventoryDto decreaseStock(Long inventoryId, StockOperationRequest req) {
        Inventory inv = repository.findByIdAndDeletedFalse(inventoryId).orElseThrow(() -> new ErrorMessageException("Inventory not found"));
        if (req.getQuantity() == null || req.getQuantity() <= 0) throw new ErrorMessageException("quantity must be a positive integer");
        int current = (inv.getStockQuantity() == null ? 0 : inv.getStockQuantity());
        if (current < req.getQuantity()) throw new ErrorMessageException("Insufficient stock to decrease");
        inv.setStockQuantity(current - req.getQuantity());
        Inventory saved = repository.save(inv);
        recordHistory(inv.getProductId(), inv.getWarehouseId(), InventoryHistoryType.STOCK_OUT, req.getQuantity(), req.getReason(), req.getReferenceId());
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public InventoryDto adjustStock(Long inventoryId, StockOperationRequest req) {
        Inventory inv = repository.findByIdAndDeletedFalse(inventoryId).orElseThrow(() -> new ErrorMessageException("Inventory not found"));
        if (req.getQuantity() == null) throw new ErrorMessageException("quantity is required for adjustment");
        inv.setStockQuantity(req.getQuantity());
        Inventory saved = repository.save(inv);
        recordHistory(inv.getProductId(), inv.getWarehouseId(), InventoryHistoryType.ADJUSTMENT, req.getQuantity(), req.getReason(), req.getReferenceId());
        return mapper.toDto(saved);
    }
}
