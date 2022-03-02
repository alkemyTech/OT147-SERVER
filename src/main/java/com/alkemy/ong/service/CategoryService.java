package com.alkemy.ong.service;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.entity.CategoryEntity;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.mapper.CategoryMapperSimple;
import com.alkemy.ong.repository.CategoryEntityRepository;
import com.alkemy.ong.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryMapper categoryMapper;
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


    public CategoryDto addCategory(CategoryDto dto) {
        CategoryEntity entity = categoryMapperSimple.categoryDTOToCategoryEntity(dto);
        CategoryEntity savedEntity =categoryRepository.save(entity);
        CategoryDto result = categoryMapperSimple.categoryEntityToCategoryDTO(savedEntity);
        return result;
    }

}
