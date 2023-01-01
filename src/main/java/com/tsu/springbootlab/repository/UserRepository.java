package com.tsu.springbootlab.repository;

import com.tsu.springbootlab.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    List<UserEntity> findAll(Specification<UserEntity> specification);

    Optional<UserEntity> findByLogin(String login);
}
