package com.billontrax.modules.inventory.services.impl;

import com.billontrax.modules.inventory.dtos.InventoryHistoryCreateRequest;
import com.billontrax.modules.inventory.dtos.InventoryHistoryDto;
import com.billontrax.modules.inventory.entities.InventoryHistory;
import com.billontrax.modules.inventory.mappers.InventoryHistoryMapper;
import com.billontrax.modules.inventory.repositories.InventoryHistoryRepository;
import com.billontrax.modules.inventory.services.InventoryHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryHistoryServiceImpl implements InventoryHistoryService {
    private final InventoryHistoryRepository repository;
    private final InventoryHistoryMapper mapper;

    @Override
    @Transactional
    public InventoryHistoryDto create(InventoryHistoryCreateRequest request) {
        InventoryHistory entity = mapper.toEntity(request);
        InventoryHistory saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public Page<InventoryHistoryDto> list(Long productId, Long warehouseId, Date from, Date to, Pageable pageable) {
        Page<InventoryHistory> page;
        if (productId != null && from != null && to != null) {
            page = repository.findByProductIdAndCreatedTimeBetween(productId, from, to, pageable);
        } else if (warehouseId != null && from != null && to != null) {
            page = repository.findByWarehouseIdAndCreatedTimeBetween(warehouseId, from, to, pageable);
        } else if (from != null && to != null) {
            page = repository.findByCreatedTimeBetween(from, to, pageable);
        } else if (productId != null) {
            page = repository.findByProductId(productId, pageable);
        } else if (warehouseId != null) {
            page = repository.findByWarehouseId(warehouseId, pageable);
        } else {
            page = repository.findAll(pageable);
        }
        List<InventoryHistoryDto> dtos = page.getContent().stream().map(mapper::toDto).collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, page.getTotalElements());
    }
}

