package com.billontrax.modules.inventory.controllers;

import com.billontrax.common.dtos.Response;
import com.billontrax.common.dtos.ResponseStatus;
import com.billontrax.common.enums.ResponseCode;
import com.billontrax.modules.inventory.dtos.InventoryCreateRequest;
import com.billontrax.modules.inventory.dtos.InventoryDto;
import com.billontrax.modules.inventory.dtos.InventoryUpdateRequest;
import com.billontrax.modules.inventory.dtos.StockOperationRequest;
import com.billontrax.modules.inventory.services.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller exposing CRUD and stock operations for Inventory.
 */
@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping
    public Response<InventoryDto> create(@Valid @RequestBody InventoryCreateRequest request) {
        Response<InventoryDto> resp = new Response<>(ResponseStatus.of(ResponseCode.CREATED, "Inventory created successfully"));
        resp.setData(inventoryService.create(request));
        return resp;
    }

    @GetMapping
    public Response<Page<InventoryDto>> list(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Long warehouseId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Response<Page<InventoryDto>> resp = new Response<>(ResponseStatus.of(ResponseCode.OK));
        resp.setData(inventoryService.list(productId, warehouseId, pageable));
        return resp;
    }

    @GetMapping("/{id}")
    public Response<InventoryDto> get(@PathVariable Long id) {
        Response<InventoryDto> resp = new Response<>(ResponseStatus.of(ResponseCode.OK));
        resp.setData(inventoryService.get(id));
        return resp;
    }

    @PutMapping("/{id}")
    public Response<InventoryDto> update(@PathVariable Long id, @RequestBody InventoryUpdateRequest request) {
        Response<InventoryDto> resp = new Response<>(ResponseStatus.of(ResponseCode.OK_NOTIFY, "Inventory updated successfully"));
        resp.setData(inventoryService.update(id, request));
        return resp;
    }

    @DeleteMapping("/{id}")
    public Response<Void> delete(@PathVariable Long id) {
        inventoryService.delete(id);
        return new Response<>(ResponseStatus.of(ResponseCode.OK_NOTIFY, "Inventory deleted successfully"));
    }

    @PostMapping("/{id}/increase")
    public Response<InventoryDto> increase(@PathVariable Long id, @Valid @RequestBody StockOperationRequest req) {
        Response<InventoryDto> resp = new Response<>(ResponseStatus.of(ResponseCode.OK_NOTIFY, "Stock increased"));
        resp.setData(inventoryService.increaseStock(id, req));
        return resp;
    }

    @PostMapping("/{id}/decrease")
    public Response<InventoryDto> decrease(@PathVariable Long id, @Valid @RequestBody StockOperationRequest req) {
        Response<InventoryDto> resp = new Response<>(ResponseStatus.of(ResponseCode.OK_NOTIFY, "Stock decreased"));
        resp.setData(inventoryService.decreaseStock(id, req));
        return resp;
    }

    @PostMapping("/{id}/adjust")
    public Response<InventoryDto> adjust(@PathVariable Long id, @Valid @RequestBody StockOperationRequest req) {
        Response<InventoryDto> resp = new Response<>(ResponseStatus.of(ResponseCode.OK_NOTIFY, "Stock adjusted"));
        resp.setData(inventoryService.adjustStock(id, req));
        return resp;
    }
}

