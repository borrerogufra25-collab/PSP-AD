package com.salesianos.dam.primerjemplo.controller;

import com.salesianos.dam.primerjemplo.dto.EditCategoryDto;
import com.salesianos.dam.primerjemplo.dto.GetCategoryDetail;
import com.salesianos.dam.primerjemplo.dto.GetCategoryList;
import com.salesianos.dam.primerjemplo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<GetCategoryList>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAll().stream().map(GetCategoryList::of).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCategoryDetail> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(GetCategoryDetail.of(categoryService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<GetCategoryDetail> addCategory(
        @RequestBody EditCategoryDto category) {
        return ResponseEntity.status(201).body(GetCategoryDetail.of(categoryService.addCategory(category.to())));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetCategoryDetail> updateCategory(
        @PathVariable Long id,
        @RequestBody EditCategoryDto category) {

        return ResponseEntity.ok(GetCategoryDetail.of(categoryService.updateCategory(id, category.to())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(
        @PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

}
