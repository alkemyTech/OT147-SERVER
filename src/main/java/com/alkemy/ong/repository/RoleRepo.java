package com.alkemy.ong.repository;

import com.alkemy.ong.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<RoleEntity, String> {
}
