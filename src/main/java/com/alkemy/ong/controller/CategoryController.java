package com.alkemy.ong.controller;


import com.alkemy.ong.entity.CategoryEntity;
import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<CategoryEntity> deleted(@PathVariable String id){

        try {
            categoryService.deletedCategoryForId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/categories")
    public ResponseEntity <List<CategoryDto>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAll());

    }
}
