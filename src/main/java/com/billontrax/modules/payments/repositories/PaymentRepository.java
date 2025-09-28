package com.billontrax.modules.payments.repositories;

import com.billontrax.modules.payments.entities.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByIdAndIsDeletedFalse(Long id);
    Page<Payment> findAllByIsDeletedFalse(Pageable pageable);
    List<Payment> findAllByIsDeletedFalse();
}
