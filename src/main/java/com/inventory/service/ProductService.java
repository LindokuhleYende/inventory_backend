package com.inventory.service;

import com.inventory.dto.*;
import com.inventory.model.Product;
import com.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.math.BigDecimal;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    public Page<Product> listAll(Optional<String> name, Optional<String> category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        if (name.isPresent()) {
            return repo.findByNameContainingIgnoreCase(name.get(), pageable);
        } else if (category.isPresent()) {
            return repo.findByCategoryIgnoreCase(category.get(), pageable);
        } else {
            return repo.findAll(pageable);
        }
    }

    public Optional<Product> getById(Long id) {
        return repo.findById(id);
    }

    public Product create(CreateProductRequest req) {
        Product p = new Product();
        p.setName(req.getName());
        p.setSku(req.getSku());
        p.setPrice(req.getPrice() == null ? BigDecimal.ZERO : req.getPrice());
        p.setStock(req.getStock() == null ? 0 : req.getStock());
        p.setCategory(req.getCategory());
        return repo.save(p);
    }

    public Optional<Product> update(Long id, UpdateProductRequest req) {
        return repo.findById(id).map(p -> {
            p.setName(req.getName());
            p.setPrice(req.getPrice());
            p.setStock(req.getStock());
            p.setCategory(req.getCategory());
            return repo.save(p);
        });
    }

    @Transactional
    public Optional<Product> changeStock(Long id, int delta) {
        return repo.findById(id).map(p -> {
            int newStock = (p.getStock() == null ? 0 : p.getStock()) + delta;
            if (newStock < 0) newStock = 0; // prevent negative stock
            p.setStock(newStock);
            return p;
        });
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
