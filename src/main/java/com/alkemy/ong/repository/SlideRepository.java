package com.alkemy.ong.repository;

import com.alkemy.ong.entity.SlideEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlideRepository extends JpaRepository<SlideEntity, String> {
 //QUERY TO GET ALL SLIDES FOR A GIVEN ORGANIZATION
 @Query(nativeQuery = true, value = "SELECT * FROM slides WHERE organization = ?1 ORDER BY order_number DESC")
 List<SlideEntity> findSlideByOrganizationId(String organizationId);


}
