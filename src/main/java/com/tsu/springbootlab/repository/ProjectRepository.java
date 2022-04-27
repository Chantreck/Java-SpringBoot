package com.tsu.springbootlab.repository;

import com.tsu.springbootlab.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer> {

}
