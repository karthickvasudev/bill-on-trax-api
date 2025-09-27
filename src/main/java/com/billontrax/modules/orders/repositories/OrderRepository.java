package com.billontrax.modules.orders.repositories;

import com.billontrax.modules.orders.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findTopByOrderByIdDesc();
}

