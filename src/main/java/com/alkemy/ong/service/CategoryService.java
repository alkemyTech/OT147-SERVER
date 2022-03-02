package com.alkemy.ong.service;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.entity.CategoryEntity;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.repository.CategoryEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

}
