package com.alkemy.ong.service;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.dto.CategoryDtoFull;
import com.alkemy.ong.dto.PagesDto;
import com.alkemy.ong.entity.CategoryEntity;
import com.alkemy.ong.exceptions.ParamNotFound;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    CategoryRepository categoryRepository;
    //Get all Category from Database.
    public List<CategoryDto> getAll() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        return categoryMapper.listCategoryEntityToListCategoryDto(categoryEntityList);
    }
    //Soft Delete Method only for ADMIN role
    public void deleteCategoryById(String id) throws Exception {
        CategoryEntity entity = this.handleFindById(id);
        categoryRepository.delete(entity);
    }
    //Method to handle category not found by id
    public CategoryEntity handleFindById(String id) throws Exception {
        Optional<CategoryEntity> FoundCategory = categoryRepository.findById(id);
        if (!FoundCategory.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return FoundCategory.get();
    }
    //Update information of category only for ADMIN role
    public CategoryDtoFull update(String id, CategoryDtoFull category) throws Exception{
            CategoryEntity categoryEntity = this.handleFindById(id);
        try {
            categoryEntity.setDescription(category.getDescription());
            categoryEntity.setImage(category.getImage());
            categoryEntity.setName(category.getName());
            categoryEntity.setDescription(category.getDescription());
            categoryEntity.setSoftDelete(category.isSoftDelete());
            categoryRepository.save(categoryEntity);
            return categoryMapper.categoryToCategoryDtoFull(categoryEntity);
        }
            catch (Exception e){
                throw new ParamNotFound(e.getMessage());
            }
    }
    //Create category only for ADMIN role
    public CategoryDto addCategory(CategoryDto categoryDto) {
        try{
        CategoryEntity entity = categoryMapper.categoryDtoToCategoryEntity(categoryDto);
        CategoryEntity savedEntity = categoryRepository.save(entity);
            return categoryMapper.categoryEntityToCategoryDto(savedEntity);}
        catch (Exception e){
            throw new ParamNotFound(e.getMessage());
        }
    }
    //Get category by id only for ADMIN role
    public CategoryDtoFull getCategory(String id) throws Exception{
        CategoryEntity categoryEntity = this.handleFindById(id);
        CategoryDtoFull categoryFullDto = CategoryMapper.categoryMapper.categoryToCategoryDtoFull(categoryEntity);
        return categoryFullDto;
    }
    //Method to get a list of categories with 10 categories User role
    @Transactional
    public PagesDto<CategoryDto> getAllForPages(int page) {
        if (page < 0) {
            throw new ParamNotFound("The page number cannot be less than 0.");
        }
        Pageable pageRequest = PageRequest.of(page, 10);
        Page<CategoryEntity> category = categoryRepository.findAll(pageRequest);
        return responsePage(category);
    }
    private PagesDto<CategoryDto> responsePage(Page<CategoryEntity> page) {
        if (page.isEmpty()) {
            throw new ParamNotFound("The page does not exist.");
        }
        Page<CategoryDto> response = new PageImpl<>(
                categoryMapper.listCategoryEntityToListCategoryDto(page.getContent()),
                PageRequest.of(page.getNumber(), page.getSize()),
                page.getTotalElements());
        return new PagesDto<>(response, "localhost:8080/categories?page=");
    }
}
