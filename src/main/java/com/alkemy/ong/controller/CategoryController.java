package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.dto.PagesDto;
import com.alkemy.ong.service.CategoryService;
import com.alkemy.ong.util.DocumentationMessages;
import com.alkemy.ong.util.DocumentationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.alkemy.ong.dto.CategoryDtoFull;

import javax.validation.Valid;



@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
@Tag(name = "Categories", description = "Create, update show and delete Categories")
public class CategoryController {
  
    private final CategoryService categoryService;
    // Method to get a list of categories with 10 categories User role
    @Tag(name = "Categories")
    @GetMapping(params = "page")
    @Operation(summary = DocumentationMessages.CATEGORY_CONTROLLER_SUMMARY_LIST)
    @ApiResponses(value = {
            @ApiResponse(responseCode = DocumentationResponse.CODE_OK,
                    description = DocumentationMessages.CATEGORY_CONTROLLER_RESPONSE_200_DESCRIPTION),
            @ApiResponse(responseCode = DocumentationResponse.CODE_BAD_REQUEST,
                    description = DocumentationMessages.CATEGORY_CONTROLLER_RESPONSE_400_DESCRIPTION),
            @ApiResponse(responseCode = DocumentationResponse.CODE_FORBIDDEN,
                    description = DocumentationMessages.CATEGORY_CONTROLLER_RESPONSE_403_DESCRIPTION)})
    public ResponseEntity<?> getPageCategory(@RequestParam(defaultValue = "0") int page) {
        PagesDto<CategoryDto> response = categoryService.getAllForPages(page);
        return ResponseEntity.ok().body(response);
    }
    //Delete by id soft delete only for ADMIN role
    @Tag(name = "Categories")
    @DeleteMapping(value = "/{id}")
    @Operation(summary = DocumentationMessages.CATEGORY_CONTROLLER_SUMMARY_DELETE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = DocumentationResponse.CODE_NO_CONTENT,
                    description = DocumentationMessages.CATEGORY_CONTROLLER_RESPONSE_204_DESCRIPTION),
            @ApiResponse(responseCode = DocumentationResponse.CODE_FORBIDDEN,
                    description = DocumentationMessages.CATEGORY_CONTROLLER_RESPONSE_403_DESCRIPTION),
            @ApiResponse(responseCode = DocumentationResponse.CODE_NOT_FOUND,
                    description = DocumentationMessages.CATEGORY_CONTROLLER_RESPONSE_404_DESCRIPTION)})
    public ResponseEntity<Void> delete(@PathVariable String id) throws Exception {
            categoryService.deleteCategoryForId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    //Create category only for ADMIN role
    @Tag(name = "Categories")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = DocumentationMessages.CATEGORY_CONTROLLER_SUMMARY_CREATE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = DocumentationResponse.CODE_CREATE,
                    description = DocumentationMessages.CATEGORY_CONTROLLER_RESPONSE_201_DESCRIPTION)})

    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDTO){
        CategoryDto savedCategory = categoryService.addCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    //Update information of category only for ADMIN role
    @Tag(name = "Categories")
    @PutMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = DocumentationMessages.CATEGORY_CONTROLLER_SUMMARY_UPDATE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = DocumentationResponse.CODE_OK,
                    description = DocumentationMessages.CATEGORY_CONTROLLER_RESPONSE_200_DESCRIPTION),
            @ApiResponse(responseCode = DocumentationResponse.CODE_FORBIDDEN,
                    description = DocumentationMessages.CATEGORY_CONTROLLER_RESPONSE_403_DESCRIPTION),
            @ApiResponse(responseCode = DocumentationResponse.CODE_NOT_FOUND,
                    description = DocumentationMessages.CATEGORY_CONTROLLER_RESPONSE_404_DESCRIPTION)})
    public ResponseEntity<CategoryDtoFull> update(@PathVariable String id, @Valid @RequestBody CategoryDtoFull categoryDtoFull)throws Exception{
        return ResponseEntity.ok(categoryService.update(id,categoryDtoFull));
    }
    //Get category by id only for ADMIN role
    @Tag(name = "Categories")
    @GetMapping(value = "/{id}")
    @Operation(summary = DocumentationMessages.CATEGORY_CONTROLLER_SUMMARY_SINGLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = DocumentationResponse.CODE_OK,
                    description = DocumentationMessages.CATEGORY_CONTROLLER_RESPONSE_200_DESCRIPTION),
            @ApiResponse(responseCode = DocumentationResponse.CODE_FORBIDDEN,
                    description = DocumentationMessages.CATEGORY_CONTROLLER_RESPONSE_403_DESCRIPTION),
            @ApiResponse(responseCode = DocumentationResponse.CODE_NOT_FOUND,
                    description = DocumentationMessages.CATEGORY_CONTROLLER_RESPONSE_404_DESCRIPTION)})
    public ResponseEntity<CategoryDtoFull> getCategory(
            @PathVariable(name="id", required=true)
                    String id) throws Exception {
        return ResponseEntity.ok(categoryService.getCategory(id));
    }
}
