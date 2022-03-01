package com.alkemy.ong.service;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.entity.CategoryEntity;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.repository.CategoryEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryMapper categoryMapper;

    @Autowired
    private CategoryEntityRepository categoryEntityRepository;

    //Get all Categories from Database
    public List<CategoryDto> getAll(){
        List<CategoryEntity>categoryEntityList=categoryEntityRepository.findAll();
        return categoryMapper.listCategoryEntityToListCategoryDto(categoryEntityList);
    }
}
