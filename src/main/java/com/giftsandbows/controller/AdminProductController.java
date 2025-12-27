package com.giftsandbows.controller;

import com.giftsandbows.model.Category;
import com.giftsandbows.model.Product;
import com.giftsandbows.repository.CategoryRepository;
import com.giftsandbows.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/products")
@CrossOrigin(origins = "*")
public class AdminProductController {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;

    public AdminProductController(ProductRepository productRepo,
                                  CategoryRepository categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    /* ================= GET ALL PRODUCTS ================= */
    @GetMapping
    public List<Product> getAll() {
        return productRepo.findAll();
    }

    /* ================= ADD PRODUCT ================= */
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> addProduct(
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @RequestParam String price,
            @RequestParam(required = false) String discountedPrice,
            @RequestParam String quantity,
            @RequestParam String categoryId,
            @RequestParam("images") MultipartFile[] images
    ) {
        try {
            if (images == null || images.length == 0) {
                return ResponseEntity.badRequest().body("Images required");
            }

            Category category = categoryRepo.findById(Long.parseLong(categoryId))
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            /* 🔑 SAFE UPLOAD PATH (ABSOLUTE) */
            Path uploadPath = Paths.get("uploads");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String imageNames = Arrays.stream(images)
                    .map(file -> {
                        try {
                            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                            Path filePath = uploadPath.resolve(filename);
                            file.transferTo(filePath.toFile());
                            return filename;
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.joining(","));

            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(Double.parseDouble(price));
            product.setDiscountedPrice(
                    discountedPrice == null || discountedPrice.isBlank()
                            ? 0
                            : Double.parseDouble(discountedPrice)
            );
            product.setQuantity(Integer.parseInt(quantity));
            product.setImageUrls(imageNames);
            product.setCategory(category);

            productRepo.save(product);
            return ResponseEntity.ok(product);

        } catch (Exception e) {
            e.printStackTrace(); // 🔥 IMPORTANT
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
