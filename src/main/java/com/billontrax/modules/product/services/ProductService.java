package com.billontrax.modules.product.services;

import com.billontrax.modules.product.dtos.ProductCreateRequest;
import com.billontrax.modules.product.dtos.ProductDto;
import com.billontrax.modules.product.dtos.ProductUpdateRequest;

import java.util.List;

public interface ProductService {
    ProductDto create(ProductCreateRequest request);
    List<ProductDto> list();
    ProductDto get(Long id);
    ProductDto update(Long id, ProductUpdateRequest request);
    void delete(Long id); // soft delete
}

