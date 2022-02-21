package com.alkemy.ong.repository;

import com.alkemy.ong.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<NewsEntity, String> {

}

