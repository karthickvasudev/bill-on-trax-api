package com.billontrax.modules.payments.controllers;

import com.billontrax.common.dtos.Response;
import com.billontrax.common.dtos.ResponseStatus;
import com.billontrax.common.enums.ResponseCode;
import com.billontrax.modules.payments.dtos.PaymentCreateRequest;
import com.billontrax.modules.payments.dtos.PaymentDto;
import com.billontrax.modules.payments.dtos.PaymentUpdateRequest;
import com.billontrax.modules.payments.services.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public Response<PaymentDto> create(@Valid @RequestBody PaymentCreateRequest request) {
        Response<PaymentDto> resp = new Response<>(ResponseStatus.of(ResponseCode.CREATED, "Payment created successfully"));
        resp.setData(paymentService.create(request));
        return resp;
    }

    @GetMapping
    public Response<List<PaymentDto>> list() {
        Response<List<PaymentDto>> resp = new Response<>(ResponseStatus.of(ResponseCode.OK));
        resp.setData(paymentService.list());
        return resp;
    }

    @GetMapping("/{id}")
    public Response<PaymentDto> get(@PathVariable Long id) {
        Response<PaymentDto> resp = new Response<>(ResponseStatus.of(ResponseCode.OK));
        resp.setData(paymentService.get(id));
        return resp;
    }

    @PutMapping("/{id}")
    public Response<PaymentDto> update(@PathVariable Long id, @RequestBody PaymentUpdateRequest request) {
        Response<PaymentDto> resp = new Response<>(ResponseStatus.of(ResponseCode.OK_NOTIFY, "Payment updated successfully"));
        resp.setData(paymentService.update(id, request));
        return resp;
    }

    @DeleteMapping("/{id}")
    public Response<Void> delete(@PathVariable Long id) {
        paymentService.delete(id);
        return new Response<>(ResponseStatus.of(ResponseCode.OK_NOTIFY, "Payment deleted successfully"));
    }
}

