package com.billontrax.modules.product.repositories;

import com.billontrax.modules.product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByIdAndIsActiveTrue(Long id);
    Optional<Product> findBySkuAndIsActiveTrue(String sku);
    List<Product> findAllByIsActiveTrue();
}

