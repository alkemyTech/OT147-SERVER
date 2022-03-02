package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.dto.CategoryDtoFull;
import com.alkemy.ong.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {
private final CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity <List<CategoryDto>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAll());
    }
    //Update information of category only for Admin User
    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryDtoFull> update(@PathVariable String id, @RequestBody CategoryDtoFull categoryDtoFull){

        return ResponseEntity.ok(categoryService.update(id,categoryDtoFull));
    }
}
