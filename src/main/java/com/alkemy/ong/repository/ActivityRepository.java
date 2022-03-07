package com.alkemy.ong.repository;

import com.alkemy.ong.entity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<ActivityEntity, String> {
}