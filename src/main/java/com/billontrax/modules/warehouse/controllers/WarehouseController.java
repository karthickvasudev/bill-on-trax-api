package com.billontrax.modules.warehouse.controllers;

import com.billontrax.common.dtos.Response;
import com.billontrax.common.dtos.ResponseStatus;
import com.billontrax.common.enums.ResponseCode;
import com.billontrax.modules.warehouse.dtos.*;
import com.billontrax.modules.warehouse.services.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
public class WarehouseController {
    private final WarehouseService warehouseService;

    @PostMapping
    public Response<WarehouseDetailDto> create(@Valid @RequestBody WarehouseCreateRequest req) {
        Response<WarehouseDetailDto> resp = new Response<>(ResponseStatus.of(ResponseCode.CREATED, "Warehouse created successfully"));
        resp.setData(warehouseService.create(req));
        return resp;
    }

    @GetMapping
    public Response<Page<WarehouseSummaryDto>> list(@RequestParam(required = false) String name,
                                                    @RequestParam(required = false) Boolean isActive,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Response<Page<WarehouseSummaryDto>> resp = new Response<>(ResponseStatus.of(ResponseCode.OK));
        resp.setData(warehouseService.list(name, isActive, pageable));
        return resp;
    }

    @GetMapping("/{id}")
    public Response<WarehouseDetailDto> get(@PathVariable Long id) {
        Response<WarehouseDetailDto> resp = new Response<>(ResponseStatus.of(ResponseCode.OK));
        resp.setData(warehouseService.get(id));
        return resp;
    }

    @PutMapping("/{id}")
    public Response<WarehouseDetailDto> update(@PathVariable Long id, @RequestBody WarehouseUpdateRequest req) {
        Response<WarehouseDetailDto> resp = new Response<>(ResponseStatus.of(ResponseCode.OK_NOTIFY, "Warehouse updated successfully"));
        resp.setData(warehouseService.update(id, req));
        return resp;
    }

    @DeleteMapping("/{id}")
    public Response<Void> delete(@PathVariable Long id) {
        warehouseService.delete(id);
        return new Response<>(ResponseStatus.of(ResponseCode.OK_NOTIFY, "Warehouse deleted successfully"));
    }
}

