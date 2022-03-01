package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper categoryMapper= Mappers.getMapper(CategoryMapper.class);

    CategoryDto categoryDtoToCategoryEntity(CategoryEntity category);

    List<CategoryDto> listCategoryEntityToListCategoryDto(List<CategoryEntity>list);

}
