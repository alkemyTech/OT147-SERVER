package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.entity.CategoryEntity;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.repository.CategoryEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryMapper categoryMapper;
    @Autowired
    private CategoryEntityRepository categoryEntityRepository;

    //Get all Category from Database.
    public List<CategoryDto> getAll(){
       List<CategoryEntity> categoryEntityList=categoryEntityRepository.findAll();

        return categoryMapper.listCategoryEntityToListCategoryDto(categoryEntityList);
    }

}
