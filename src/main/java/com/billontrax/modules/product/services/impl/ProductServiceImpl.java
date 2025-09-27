package com.billontrax.modules.product.services.impl;

import com.billontrax.common.exceptions.ErrorMessageException;
import com.billontrax.modules.product.dtos.ProductCreateRequest;
import com.billontrax.modules.product.dtos.ProductDto;
import com.billontrax.modules.product.dtos.ProductUpdateRequest;
import com.billontrax.modules.product.entities.Product;
import com.billontrax.modules.product.enums.ProductType;
import com.billontrax.modules.product.mappers.ProductMapper;
import com.billontrax.modules.product.repositories.ProductRepository;
import com.billontrax.modules.product.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Override
    @Transactional
    public ProductDto create(ProductCreateRequest request) {
        log.info("Creating product: {}", request);
        validateInventoryFields(request.getType(), request.getStockQuantity(), request.getCostPrice());
        try {
            Product entity = mapper.toEntity(request);
            // Ensure active default
            if (entity.getIsActive() == null) entity.setIsActive(true);
            Product saved = repository.save(entity);
            return mapper.toDto(saved);
        } catch (DataIntegrityViolationException e) {
            throw new ErrorMessageException(String.format("Product with SKU %s already exists", request.getSku()));
        }
    }

    @Override
    public List<ProductDto> list() {
        return repository.findAllByIsActiveTrue().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto get(Long id) {
        Product product = repository.findByIdAndIsActiveTrue(id).orElseThrow(() -> new ErrorMessageException("Product not found"));
        return mapper.toDto(product);
    }

    @Override
    @Transactional
    public ProductDto update(Long id, ProductUpdateRequest request) {
        Product product = repository.findByIdAndIsActiveTrue(id).orElseThrow(() -> new ErrorMessageException("Product not found"));
        // If changing to PHYSICAL ensure inventory fields present
        if (request.getType() != null) {
            if (request.getType() == ProductType.PHYSICAL) {
                validateInventoryFields(ProductType.PHYSICAL, request.getStockQuantity() != null ? request.getStockQuantity() : product.getStockQuantity(), request.getCostPrice() != null ? request.getCostPrice() : product.getCostPrice());
            } else {
                // Null out inventory fields when switching to non PHYSICAL
                product.setStockQuantity(null);
                product.setCostPrice(null);
                product.setLowStockAlert(null);
                product.setWarehouseId(null);
                product.setReorderLevel(null);
            }
        }
        mapper.updateEntityFromDto(request, product);
        Product saved = repository.save(product);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Product product = repository.findByIdAndIsActiveTrue(id).orElseThrow(() -> new ErrorMessageException("Product not found"));
        product.setIsActive(false); // soft delete
        repository.save(product);
    }

    private void validateInventoryFields(ProductType type, Integer stockQuantity, java.math.BigDecimal costPrice) {
        if (type == ProductType.PHYSICAL) {
            if (stockQuantity == null) {
                throw new ErrorMessageException("stockQuantity is required for PHYSICAL products");
            }
            if (costPrice == null) {
                throw new ErrorMessageException("costPrice is required for PHYSICAL products");
            }
        }
    }
}

