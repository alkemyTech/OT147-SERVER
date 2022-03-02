package com.alkemy.ong.service;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.dto.CategoryDtoFull; main
import com.alkemy.ong.entity.CategoryEntity;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.repository.CategoryEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
  
    @Autowired
    private CategoryEntityRepository categoryEntityRepository;

    //Get all Category from Database.
    public List<CategoryDto> getAll(){
       List<CategoryEntity> categoryEntityList=categoryEntityRepository.findAll();

        return categoryMapper.listCategoryEntityToListCategoryDto(categoryEntityList);
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
}
