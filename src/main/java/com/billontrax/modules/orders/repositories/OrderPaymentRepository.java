package com.billontrax.modules.orders.repositories;

import com.billontrax.modules.orders.entities.OrderPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPaymentRepository extends JpaRepository<OrderPayment, Long> {
}

