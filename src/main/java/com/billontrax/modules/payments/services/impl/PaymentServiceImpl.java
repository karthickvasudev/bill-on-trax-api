package com.billontrax.modules.payments.services.impl;

import com.billontrax.common.exceptions.ErrorMessageException;
import com.billontrax.modules.payments.dtos.PaymentCreateRequest;
import com.billontrax.modules.payments.dtos.PaymentDto;
import com.billontrax.modules.payments.dtos.PaymentUpdateRequest;
import com.billontrax.modules.payments.entities.Payment;
import com.billontrax.modules.payments.mappers.PaymentMapper;
import com.billontrax.modules.payments.repositories.PaymentRepository;
import com.billontrax.modules.payments.services.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    @Transactional
    public PaymentDto create(PaymentCreateRequest request) {
        log.info("Creating payment: {}", request);
        Payment entity = paymentMapper.toEntity(request);
        if (entity.getPaymentDate() == null) {
            entity.setPaymentDate(LocalDateTime.now());
        }
        if (entity.getDiscountGiven() == null) {
            entity.setDiscountGiven(BigDecimal.ZERO);
        }
        entity.setIsDeleted(false);
        // First save to get ID for code generation
        Payment saved = paymentRepository.save(entity);
        if (saved.getPaymentNumber() == null) {
            String code = "PAY-" + String.format("%05d", saved.getId());
            saved.setPaymentNumber(code);
            saved = paymentRepository.save(saved);
        }
        return paymentMapper.toDto(saved);
    }

    @Override
    @Transactional
    public PaymentDto update(Long id, PaymentUpdateRequest request) {
        Payment entity = paymentRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ErrorMessageException("Payment not found"));
        paymentMapper.updateEntityFromDto(request, entity);
        if (request.getPaymentDate() != null) {
            entity.setPaymentDate(request.getPaymentDate());
        }
        if (entity.getDiscountGiven() == null) {
            entity.setDiscountGiven(BigDecimal.ZERO);
        }
        Payment updated = paymentRepository.save(entity);
        return paymentMapper.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentDto get(Long id) {
        return paymentRepository.findByIdAndIsDeletedFalse(id)
                .map(paymentMapper::toDto)
                .orElseThrow(() -> new ErrorMessageException("Payment not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDto> list() {
        return paymentRepository.findAllByIsDeletedFalse().stream()
                .map(paymentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Payment entity = paymentRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ErrorMessageException("Payment not found"));
        entity.setIsDeleted(true);
        paymentRepository.save(entity);
    }
}
