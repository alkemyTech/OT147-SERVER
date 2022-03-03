package com.alkemy.ong.service;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.dto.CategoryDtoFull;
import com.alkemy.ong.entity.CategoryEntity;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryMapper categoryMapper;

    @Autowired
    CategoryRepository categoryRepository;


    //Get all Category from Database.
    public List<CategoryDto> getAll(){
       List<CategoryEntity> categoryEntityList=categoryRepository.findAll();

        return categoryMapper.listCategoryEntityToListCategoryDto(categoryEntityList);
    }

    public void deletedCategoryForId(String id) throws Exception {
        CategoryEntity entity = this.handleFindById(id);
        categoryRepository.delete(entity);
    }

    /*Method for Exist Category */
    public CategoryEntity handleFindById(String id) throws Exception {
        Optional<CategoryEntity> NoFoundCategory = categoryRepository.findById(id);
        if (!NoFoundCategory.isPresent()) {
            throw new Exception("The category does not exits:"+id);
        }
        return NoFoundCategory.get();
    }

    //Update Category
    public CategoryDtoFull update(String id,CategoryDtoFull category) {
        if (categoryRepository.findById(id).isPresent()) {
                CategoryEntity categoryEntity = categoryRepository.findById(id).get();
                categoryEntity.setDescription(category.getDescription());
                categoryEntity.setImage(category.getImage());
                categoryEntity.setName(category.getName());
                categoryEntity.setDescription(category.getDescription());
                categoryEntity.setSoftDelete(category.isSoftDelete());
                categoryRepository.save(categoryEntity);
                return categoryMapper.categoryToCategoryDtoFull(categoryEntity);
       }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is no Category with the entered Id");
        }
    }

    //create Category
    public CategoryDto addCategory(CategoryDto categoryDto) {
        CategoryEntity entity = categoryMapper.categoryDtoToCategoryEntity(categoryDto);
        CategoryEntity savedEntity =categoryRepository.save(entity);
        return categoryMapper.categoryEntityToCategoryDto(savedEntity);
    }
}
