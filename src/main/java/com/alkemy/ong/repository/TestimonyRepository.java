package com.alkemy.ong.repository;


import com.alkemy.ong.entity.TestimonyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestimonyRepository extends JpaRepository<TestimonyEntity, String> {
}
