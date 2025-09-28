package com.billontrax.modules.payments.services;

import com.billontrax.modules.payments.dtos.PaymentCreateRequest;
import com.billontrax.modules.payments.dtos.PaymentDto;
import com.billontrax.modules.payments.dtos.PaymentUpdateRequest;

import java.util.List;

public interface PaymentService {
    PaymentDto create(PaymentCreateRequest request);
    PaymentDto update(Long id, PaymentUpdateRequest request);
    PaymentDto get(Long id);
    List<PaymentDto> list();
    void delete(Long id);
}

