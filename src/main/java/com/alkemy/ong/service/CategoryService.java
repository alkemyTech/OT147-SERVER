package com.alkemy.ong.service;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.dto.CategoryDtoFull; 
import com.alkemy.ong.entity.CategoryEntity;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.mapper.CategoryMapperSimple;
import com.alkemy.ong.repository.CategoryEntityRepository;
import com.alkemy.ong.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryMapper categoryMapper;


    @Autowired
    private CategoryEntityRepository categoryEntityRepository;
    @Autowired
    CategoryMapperSimple categoryMapperSimple;
    @Autowired
    CategoryRepository categoryRepository;


    //Get all Category from Database.
    public List<CategoryDto> getAll(){
       List<CategoryEntity> categoryEntityList=categoryEntityRepository.findAll();

        return categoryMapper.listCategoryEntityToListCategoryDto(categoryEntityList);
    }

    public void deletedCategoryForId(String id) throws Exception {
        CategoryEntity entity = this.handleFindById(id);
        categoryEntityRepository.delete(entity);
    }

    /*Method for Exist Category */
    public CategoryEntity handleFindById(String id) throws Exception {
        Optional<CategoryEntity> NoFoundCategory = categoryEntityRepository.findById(id);
        if (!NoFoundCategory.isPresent()) {
            throw new Exception("The category does not exits:"+id);
        }
        return NoFoundCategory.get();
    }

    //Update Category
    public CategoryDtoFull update(String id,CategoryDtoFull category) {
        if (categoryEntityRepository.findById(id).isPresent()) {
                CategoryEntity categoryEntity = categoryEntityRepository.findById(id).get();
                categoryEntity.setDescription(category.getDescription());
                categoryEntity.setImage(category.getImage());
                categoryEntity.setName(category.getName());
                categoryEntity.setDescription(category.getDescription());
                categoryEntity.setSoftDelete(category.isSoftDelete());
                categoryEntityRepository.save(categoryEntity);
                return categoryMapper.categoryToCategoryDtoFull(categoryEntity);
       }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is no Category with the entered Id");
        }
    }

    //create Category
    public CategoryDto addCategory(CategoryDto dto) {
        CategoryEntity entity = categoryMapperSimple.categoryDTOToCategoryEntity(dto);
        CategoryEntity savedEntity =categoryRepository.save(entity);
        CategoryDto result = categoryMapperSimple.categoryEntityToCategoryDTO(savedEntity);
        return result;
    }
}
