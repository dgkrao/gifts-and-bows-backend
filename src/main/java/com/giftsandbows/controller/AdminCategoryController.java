package com.giftsandbows.controller;

import com.giftsandbows.model.Category;
import com.giftsandbows.repository.CategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/categories")
@CrossOrigin(origins = "*")
public class AdminCategoryController {

    private final CategoryRepository categoryRepository;

    public AdminCategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /* ================= GET ALL ================= */
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /* ================= ADD ================= */
    @PostMapping
    public Category addCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    /* ================= DELETE ================= */
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
    }
}
