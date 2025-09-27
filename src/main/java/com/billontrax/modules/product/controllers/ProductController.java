package com.billontrax.modules.product.controllers;

import com.billontrax.common.dtos.Response;
import com.billontrax.common.dtos.ResponseStatus;
import com.billontrax.common.enums.ResponseCode;
import com.billontrax.modules.product.dtos.ProductCreateRequest;
import com.billontrax.modules.product.dtos.ProductDto;
import com.billontrax.modules.product.dtos.ProductUpdateRequest;
import com.billontrax.modules.product.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing CRUD operations for Products.
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    /** Create a new product */
    @PostMapping
    public Response<ProductDto> create(@Valid @RequestBody ProductCreateRequest request) {
        Response<ProductDto> response = new Response<>(ResponseStatus.of(ResponseCode.CREATED, "Product created successfully"));
        response.setData(productService.create(request));
        return response;
    }

    /** List all active products */
    @GetMapping
    public Response<List<ProductDto>> list() {
        Response<List<ProductDto>> response = new Response<>(ResponseStatus.of(ResponseCode.OK));
        response.setData(productService.list());
        return response;
    }

    /** Get product by id */
    @GetMapping("/{id}")
    public Response<ProductDto> get(@PathVariable Long id) {
        Response<ProductDto> response = new Response<>(ResponseStatus.of(ResponseCode.OK));
        response.setData(productService.get(id));
        return response;
    }

    /** Update product by id */
    @PutMapping("/{id}")
    public Response<ProductDto> update(@PathVariable Long id, @RequestBody ProductUpdateRequest request) {
        Response<ProductDto> response = new Response<>(ResponseStatus.of(ResponseCode.OK_NOTIFY, "Product updated successfully"));
        response.setData(productService.update(id, request));
        return response;
    }

    /** Soft delete product by id (sets isActive=false) */
    @DeleteMapping("/{id}")
    public Response<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return new Response<>(ResponseStatus.of(ResponseCode.OK_NOTIFY, "Product deleted successfully"));
    }
}

