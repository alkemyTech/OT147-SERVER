package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.dto.PagesDto;
import com.alkemy.ong.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.alkemy.ong.dto.CategoryDtoFull;
import javax.validation.Valid;



@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {
  
    private final CategoryService categoryService;


    @GetMapping
    public ResponseEntity<?> getPageCategory(@RequestParam(defaultValue = "0") int page) {
        PagesDto<CategoryDto> response = categoryService.getAllForPages(page);
        return ResponseEntity.ok().body(response);
    }

    //deleted by id soft delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleted(@PathVariable String id) throws Exception {
        try {
            categoryService.deletedCategoryForId(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    //Create category
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

    //Get category by id only for ADMIN
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDtoFull> getCategory(
            @PathVariable(name="id", required=true)
                    String id){
        return ResponseEntity.ok(categoryService.getCategory(id));
    }
}
