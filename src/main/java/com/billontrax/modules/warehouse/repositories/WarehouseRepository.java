package com.billontrax.modules.warehouse.repositories;

import com.billontrax.modules.warehouse.entities.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    Optional<Warehouse> findByIdAndIsActiveTrue(Long id);
    Optional<Warehouse> findByNameIgnoreCase(String name);

    Page<Warehouse> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Warehouse> findByIsActive(Boolean isActive, Pageable pageable);
    Page<Warehouse> findByNameContainingIgnoreCaseAndIsActive(String name, Boolean isActive, Pageable pageable);
}

