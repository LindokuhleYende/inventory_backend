package com.inventory.repository;

import com.inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findBySku(String sku);

    // For simple name search
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // For category filter
    Page<Product> findByCategoryIgnoreCase(String category, Pageable pageable);
}