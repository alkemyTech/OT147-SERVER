package com.alkemy.ong.service;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.dto.CategoryDtoFull;
import com.alkemy.ong.dto.PagesDto;
import com.alkemy.ong.entity.CategoryEntity;
import com.alkemy.ong.exceptions.ParamNotFound;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryMapper categoryMapper;

    @Autowired
    CategoryRepository categoryRepository;


    //Get all Category from Database.
    public List<CategoryDto> getAll() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();

        return categoryMapper.listCategoryEntityToListCategoryDto(categoryEntityList);
    }

    public void deleteCategoryForId(String id) throws Exception {
        CategoryEntity entity = this.handleFindById(id);
        categoryRepository.delete(entity);
    }

    /*Method for Exist Category */
    public CategoryEntity handleFindById(String id) throws Exception {
        Optional<CategoryEntity> NoFoundCategory = categoryRepository.findById(id);
        if (!NoFoundCategory.isPresent()) {
            throw new ParamNotFound("The category does not exits:" + id);
        }
        return NoFoundCategory.get();
    }

    //Update Category
    public CategoryDtoFull update(String id, CategoryDtoFull category) throws Exception {
        if (categoryRepository.findById(id).isPresent()) {
            CategoryEntity categoryEntity = categoryRepository.findById(id).get();
            categoryEntity.setDescription(category.getDescription());
            categoryEntity.setImage(category.getImage());
            categoryEntity.setName(category.getName());
            categoryEntity.setDescription(category.getDescription());
            categoryEntity.setSoftDelete(category.isSoftDelete());
            categoryRepository.save(categoryEntity);
            return categoryMapper.categoryToCategoryDtoFull(categoryEntity);
        } else {
            throw new ParamNotFound("There is no Category with the entered Id");
        }
    }

    //create Category
    public CategoryDto addCategory(CategoryDto categoryDto) {
        CategoryEntity entity = categoryMapper.categoryDtoToCategoryEntity(categoryDto);
        CategoryEntity savedEntity = categoryRepository.save(entity);
        return categoryMapper.categoryEntityToCategoryDto(savedEntity);
    }
    //Get category by id
    public CategoryDtoFull getCategory(String id)  throws Exception{
        CategoryEntity category = this.handleFindById(id);
        CategoryDtoFull categoryFullDto = CategoryMapper.categoryMapper.categoryToCategoryDtoFull(category);
        return categoryFullDto;
    }
    //Get categories for pages
    @Transactional
    public PagesDto<CategoryDto> getAllForPages(int page) {
        if (page < 0) {
            throw new ParamNotFound("The page number cannot be less than 0.");
        }
        Pageable pageRequest = PageRequest.of(page, 10);
        Page<CategoryEntity> category = categoryRepository.findAll(pageRequest);
        return responsePage(category);
    }

    private PagesDto<CategoryDto> responsePage(Page<CategoryEntity> page) {
        if (page.isEmpty()) {
            throw new ParamNotFound("The page does not exist.");
        }
        Page<CategoryDto> response = new PageImpl<>(
                categoryMapper.listCategoryEntityToListCategoryDto(page.getContent()),
                PageRequest.of(page.getNumber(), page.getSize()),
                page.getTotalElements());
        return new PagesDto<>(response, "localhost:8080/categories?page=");
    }
}
