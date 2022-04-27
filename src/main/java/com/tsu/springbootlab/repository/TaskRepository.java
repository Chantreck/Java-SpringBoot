package com.tsu.springbootlab.repository;

import com.tsu.springbootlab.entity.ProjectEntity;
import com.tsu.springbootlab.entity.TaskEntity;
import com.tsu.springbootlab.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {
    List<TaskEntity> findAllByProject(ProjectEntity project);

    List<TaskEntity> findAllByPerformer(UserEntity performer);

    List<TaskEntity> findAll(Specification<TaskEntity> specification);
}