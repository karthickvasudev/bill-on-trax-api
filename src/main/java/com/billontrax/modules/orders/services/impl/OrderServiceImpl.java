package com.billontrax.modules.orders.services.impl;

import com.billontrax.modules.orders.dtos.OrderItemDto;
import com.billontrax.modules.orders.dtos.OrderPaymentDto;
import com.billontrax.modules.orders.dtos.OrderRequestDto;
import com.billontrax.modules.orders.dtos.OrderResponseDto;
import com.billontrax.modules.orders.entities.Order;
import com.billontrax.modules.orders.entities.OrderItem;
import com.billontrax.modules.orders.entities.OrderPayment;
import com.billontrax.modules.orders.enums.PaymentStatus;
import com.billontrax.modules.orders.enums.OrderStatus;
import com.billontrax.modules.orders.repositories.OrderPaymentRepository;
import com.billontrax.modules.orders.repositories.OrderRepository;
import com.billontrax.modules.orders.repositories.OrderItemRepository;
import com.billontrax.modules.orders.mappers.OrderMapper;
import com.billontrax.modules.orders.services.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderPaymentRepository orderPaymentRepository;
    private final OrderMapper mapper;

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto dto) {
        Order order = mapper.toEntity(dto);
        // compute items totals
        computeTotals(order);
        // persist to get id
        order = orderRepository.save(order);
        // generate order number based on id if not present
        if (order.getOrderNumber() == null || order.getOrderNumber().isBlank()) {
            order.setOrderNumber(String.format("ORD-%05d", order.getId()));
        }
        // set createdBy if provided in dto
        if (dto.getCreatedBy() != null) {
            order.setCreatedBy(dto.getCreatedBy());
        }
        // compute payment status
        updatePaymentStatus(order);
        order = orderRepository.save(order);
        return mapper.toDto(order);
    }

    @Override
    @Transactional
    public OrderResponseDto updateOrder(Long id, OrderRequestDto dto) {
        Optional<Order> opt = orderRepository.findById(id);
        if (opt.isEmpty()) throw new IllegalArgumentException("Order not found: " + id);
        Order order = opt.get();
        // remove existing children and map from dto
        order.getOrderItems().clear();
        order.getOrderPayments().clear();

        mapper.updateEntityFromDto(dto, order);
        // ensure id and orderNumber preserved
        order.setId(id);
        computeTotals(order);
        if (dto.getCreatedBy() != null) order.setCreatedBy(dto.getCreatedBy());
        updatePaymentStatus(order);
        order = orderRepository.save(order);
        return mapper.toDto(order);
    }

    @Override
    public OrderResponseDto getOrderById(Long id) {
        return orderRepository.findById(id).map(mapper::toDto).orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
    }

    @Override
    public Page<OrderResponseDto> listOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    @Transactional
    public OrderResponseDto updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
        order.setStatus(status);
        order = orderRepository.save(order);
        return mapper.toDto(order);
    }

    @Override
    @Transactional
    public void softDelete(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
        order.setIsDeleted(true);
        orderRepository.save(order);
    }

    private void computeTotals(Order order) {
        List<OrderItem> items = order.getOrderItems();
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal itemsTaxTotal = BigDecimal.ZERO;
        for (OrderItem it : items) {
            if (it.getQuantity() == null) it.setQuantity(0);
            if (it.getUnitPrice() == null) it.setUnitPrice(BigDecimal.ZERO);
            BigDecimal totalPrice = it.getUnitPrice().multiply(BigDecimal.valueOf(it.getQuantity()));
            totalPrice = totalPrice.setScale(2, RoundingMode.HALF_UP);
            it.setTotalPrice(totalPrice);
            BigDecimal taxAmt = BigDecimal.ZERO;
            if (it.getTaxPercentage() != null) {
                taxAmt = totalPrice.multiply(it.getTaxPercentage()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            }
            it.setFinalPrice(totalPrice.add(taxAmt));
            total = total.add(totalPrice);
            itemsTaxTotal = itemsTaxTotal.add(taxAmt);
            it.setOrder(order);
        }
        order.setTotalAmount(total.setScale(2, RoundingMode.HALF_UP));
        BigDecimal discount = order.getDiscountAmount() == null ? BigDecimal.ZERO : order.getDiscountAmount();
        BigDecimal tax = order.getTaxAmount() == null ? itemsTaxTotal : order.getTaxAmount();
        order.setFinalAmount(total.subtract(discount).add(tax).setScale(2, RoundingMode.HALF_UP));
        // attach payments' order reference
        for (OrderPayment p : order.getOrderPayments()) {
            p.setOrder(order);
        }
    }

    private void updatePaymentStatus(Order order) {
        BigDecimal sumPayments = order.getOrderPayments().stream()
                .map(p -> p.getAmount() == null ? BigDecimal.ZERO : p.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal finalAmt = order.getFinalAmount() == null ? BigDecimal.ZERO : order.getFinalAmount();
        if (sumPayments.compareTo(BigDecimal.ZERO) == 0) {
            order.setPaymentStatus(PaymentStatus.PENDING);
        } else if (sumPayments.compareTo(finalAmt) >= 0) {
            order.setPaymentStatus(PaymentStatus.PAID);
        } else {
            order.setPaymentStatus(PaymentStatus.PARTIALLY_PAID);
        }
    }
}

