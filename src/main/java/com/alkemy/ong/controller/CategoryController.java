package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.entity.CategoryEntity;

import com.alkemy.ong.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity <List<CategoryDto>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAll());
    }
    //deleted by id soft delete
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleted(@PathVariable String id) throws Exception {

        try {
            categoryService.deletedCategoryForId(id);
        } catch (Exception e) {
            throw  new Exception(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
