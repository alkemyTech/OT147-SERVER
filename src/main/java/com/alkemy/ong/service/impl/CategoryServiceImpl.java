package com.alkemy.ong.service.impl;

import com.alkemy.ong.entity.CategoryEntity;
import com.alkemy.ong.repository.CategoryEntityRepository;
import com.alkemy.ong.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryEntityRepository categoryRepo;

    @Override
    public void deletedCategoryForId(String id) throws Exception {
        CategoryEntity entity = this.handleFindById(id);
        categoryRepo.delete(entity);
    }
    /*Method for Exist Category */
    public CategoryEntity handleFindById(String id) throws Exception {
        Optional<CategoryEntity> NoFoundCategory = categoryRepo.findById(id);
        if (!NoFoundCategory.isPresent()) {
            throw new Exception("The category no exist:"+id);
        }
        return NoFoundCategory.get();
    }
}
