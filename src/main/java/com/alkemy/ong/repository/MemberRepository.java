package com.alkemy.ong.repository;

import com.alkemy.ong.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository  extends JpaRepository <MemberEntity, String> {
   List<MemberEntity> findAll();
}
