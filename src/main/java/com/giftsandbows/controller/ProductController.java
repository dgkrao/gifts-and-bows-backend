package com.giftsandbows.controller;

import com.giftsandbows.model.Product;
import com.giftsandbows.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductRepository productRepo;

    public ProductController(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping
    public List<Product> all() {
        return productRepo.findAll();
    }

    @GetMapping("/category/{id}")
    public List<Product> byCategory(@PathVariable Long id) {
        return productRepo.findByCategory_Id(id);
    }

    @GetMapping("/{id}")
    public Product byId(@PathVariable Long id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
