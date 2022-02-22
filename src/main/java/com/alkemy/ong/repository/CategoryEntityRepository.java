package com.alkemy.ong.repository;


import com.alkemy.ong.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryEntityRepository extends JpaRepository<CategoryEntity, String> {
}