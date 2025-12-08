package com.inventory.controller;

import com.inventory.dto.*;
import com.inventory.model.Product;
import com.inventory.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Product management/Store inventory APIs")
public class ProductController {

    @Autowired
    private ProductService service;

    private ProductDTO toDto(Product p) {
        return new ProductDTO(p.getId(), p.getName(), p.getSku(), p.getPrice(), p.getStock(), p.getCategory());
    }

    @GetMapping
    @Operation(summary = "List products", description = "Get paginated list of products. Optional filters: name, category")
    public ResponseEntity<?> list(
            @RequestParam Optional<String> name,
            @RequestParam Optional<String> category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<Product> result = service.listAll(name, category, page, size);
        var body = result.map(this::toDto);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by id")
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id) {
        return service.getById(id).map(p -> ResponseEntity.ok(toDto(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create product")
    public ResponseEntity<ProductDTO> create(@Validated @RequestBody CreateProductRequest req) {
        Product created = service.create(req);
        return ResponseEntity.created(URI.create("/api/products/" + created.getId())).body(toDto(created));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id,
                                             @Validated @RequestBody UpdateProductRequest req) {
        return service.update(id, req).map(p -> ResponseEntity.ok(toDto(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/stock")
    @Operation(summary = "Update stock by delta")
    public ResponseEntity<ProductDTO> updateStock(@PathVariable Long id,
                                                  @Validated @RequestBody StockUpdateRequest req) {
        return service.changeStock(id, req.getDelta()).map(this::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

