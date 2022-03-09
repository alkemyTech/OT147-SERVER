package com.alkemy.ong.repository;

import com.alkemy.ong.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface MemberRepository  extends JpaRepository <MemberEntity, String> {
   }
