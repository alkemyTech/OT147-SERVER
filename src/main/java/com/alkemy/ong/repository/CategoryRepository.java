package com.alkemy.ong.repository;

import com.alkemy.ong.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}