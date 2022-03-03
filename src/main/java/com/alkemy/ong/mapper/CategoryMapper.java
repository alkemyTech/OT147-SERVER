package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.dto.CategoryDtoFull;
import com.alkemy.ong.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper (componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper categoryMapper= Mappers.getMapper(CategoryMapper.class);

    CategoryEntity categoryDtoToCategoryEntity(CategoryDto categoryDto);

    CategoryDto categoryEntityToCategoryDto(CategoryEntity categoryEntity);

    List<CategoryDto> listCategoryEntityToListCategoryDto(List<CategoryEntity>list);

    CategoryDtoFull categoryToCategoryDtoFull(CategoryEntity entity);

    CategoryEntity categoryFullDtoToCategoryEntity(CategoryDtoFull dto);

}
