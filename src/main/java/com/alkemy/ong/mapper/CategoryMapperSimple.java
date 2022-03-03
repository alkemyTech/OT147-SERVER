package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapperSimple {

    public CategoryEntity categoryDTOToCategoryEntity(CategoryDto categoryDTO){
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryDTO.getName());
        categoryEntity.setDescription(categoryDTO.getDescription());
        categoryEntity.setImage(categoryDTO.getImage());
        return categoryEntity;
    }


    public  CategoryDto categoryEntityToCategoryDTO(CategoryEntity entity){
        CategoryDto categoryDTO = new CategoryDto();
        categoryDTO.setName(entity.getName());
        categoryDTO.setName(entity.getDescription());
        categoryDTO.setName(entity.getImage());
        return categoryDTO;
    }
}
