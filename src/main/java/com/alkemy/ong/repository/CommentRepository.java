package com.alkemy.ong.repository;

import com.alkemy.ong.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, String> {
    //QUERY TO GET ALL COMMENTS ORDER
    @Query(nativeQuery = true, value ="SELECT * FROM comments ORDER BY timestamps ASC")
    List<CommentEntity> findAllByOrder();
}