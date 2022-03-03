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
import com.alkemy.ong.dto.CategoryDtoFull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {
  
    private final CategoryService categoryService;


    @GetMapping("/")

    public ResponseEntity <List<CategoryDto>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAll());
    }

    //deleted by id soft delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleted(@PathVariable String id) throws Exception {

        try {
            categoryService.deletedCategoryForId(id);
        } catch (Exception e) {
            throw  new Exception(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDTO){
        CategoryDto savedCategory = categoryService.addCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    //Update information of category only for Admin User
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDtoFull> update(@PathVariable String id, @RequestBody CategoryDtoFull categoryDtoFull){

        return ResponseEntity.ok(categoryService.update(id,categoryDtoFull));
    }
}
