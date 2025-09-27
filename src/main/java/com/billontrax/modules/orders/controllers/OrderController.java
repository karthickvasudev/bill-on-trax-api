package com.billontrax.modules.orders.controllers;

import com.billontrax.common.dtos.Response;
import com.billontrax.common.dtos.ResponseStatus;
import com.billontrax.common.enums.ResponseCode;
import com.billontrax.modules.orders.dtos.OrderRequestDto;
import com.billontrax.modules.orders.dtos.OrderResponseDto;
import com.billontrax.modules.orders.dtos.OrderStatusUpdateDto;
import com.billontrax.modules.orders.enums.OrderStatus;
import com.billontrax.modules.orders.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    public Response<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto dto) {
        Response<OrderResponseDto> resp = new Response<>(ResponseStatus.of(ResponseCode.CREATED, "Order created successfully"));
        resp.setData(service.createOrder(dto));
        return resp;
    }

    @GetMapping
    public Response<Page<OrderResponseDto>> listOrders(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Response<Page<OrderResponseDto>> resp = new Response<>(ResponseStatus.of(ResponseCode.OK));
        resp.setData(service.listOrders(pageable));
        return resp;
    }

    @GetMapping("/{id}")
    public Response<OrderResponseDto> getOrder(@PathVariable Long id) {
        Response<OrderResponseDto> resp = new Response<>(ResponseStatus.of(ResponseCode.OK));
        resp.setData(service.getOrderById(id));
        return resp;
    }

    @PutMapping("/{id}")
    public Response<OrderResponseDto> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderRequestDto dto) {
        Response<OrderResponseDto> resp = new Response<>(ResponseStatus.of(ResponseCode.OK_NOTIFY, "Order updated successfully"));
        resp.setData(service.updateOrder(id, dto));
        return resp;
    }

    @PatchMapping("/{id}/status")
    public Response<OrderResponseDto> updateStatus(@PathVariable Long id, @Valid @RequestBody OrderStatusUpdateDto dto) {
        Response<OrderResponseDto> resp = new Response<>(ResponseStatus.of(ResponseCode.OK_NOTIFY, "Order status updated"));
        resp.setData(service.updateOrderStatus(id, dto.getStatus()));
        return resp;
    }

    @DeleteMapping("/{id}")
    public Response<Void> deleteOrder(@PathVariable Long id) {
        service.softDelete(id);
        return new Response<>(ResponseStatus.of(ResponseCode.OK_NOTIFY, "Order deleted successfully"));
    }
}
