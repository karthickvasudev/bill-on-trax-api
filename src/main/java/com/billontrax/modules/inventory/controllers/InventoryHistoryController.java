package com.billontrax.modules.inventory.controllers;

import com.billontrax.common.dtos.Response;
import com.billontrax.common.dtos.ResponseStatus;
import com.billontrax.common.enums.ResponseCode;
import com.billontrax.modules.inventory.dtos.InventoryHistoryCreateRequest;
import com.billontrax.modules.inventory.dtos.InventoryHistoryDto;
import com.billontrax.modules.inventory.services.InventoryHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Controller exposing inventory history endpoints.
 */
@RestController
@RequestMapping("/api/inventory/history")
@RequiredArgsConstructor
public class InventoryHistoryController {
    private final InventoryHistoryService historyService;

    @PostMapping
    public Response<InventoryHistoryDto> create(@Valid @RequestBody InventoryHistoryCreateRequest req) {
        Response<InventoryHistoryDto> resp = new Response<>(ResponseStatus.of(ResponseCode.CREATED, "Inventory history recorded"));
        resp.setData(historyService.create(req));
        return resp;
    }

    @GetMapping
    public Response<Page<InventoryHistoryDto>> list(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Long warehouseId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Response<Page<InventoryHistoryDto>> resp = new Response<>(ResponseStatus.of(ResponseCode.OK));
        resp.setData(historyService.list(productId, warehouseId, from, to, pageable));
        return resp;
    }
}

