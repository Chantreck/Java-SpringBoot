package com.tsu.springbootlab.repository;

import com.tsu.springbootlab.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    List<CommentEntity> findAllByTextContaining(String text);
}