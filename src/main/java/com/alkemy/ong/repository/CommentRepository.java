package com.alkemy.ong.repository;

import com.alkemy.ong.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, String> {
    //Query to list comments of a given NewsId
    @Query(nativeQuery = true, value ="SELECT * FROM comments WHERE comments.news = :id")
    List<CommentEntity> findCommentsByNewsId(String id);
}