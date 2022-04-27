package com.tsu.springbootlab.dto.converters;

import com.google.common.hash.Hashing;
import com.tsu.springbootlab.dto.UserCreateDto;
import com.tsu.springbootlab.dto.UserDto;
import com.tsu.springbootlab.entity.UserEntity;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
public class UserDto {
    private Integer id;
    private Date createdAt;
    private Date editedAt;
    private String name;
    private String login;
    private String password;
    private Role role;
    private List<TaskDto> createdTasks;
    private List<TaskDto> assignedTasks;
    private List<CommentDto> comments;
}
 */

public class UserDtoConverter {
    public static UserEntity convertDtoToEntity(UserCreateDto dto) {
        var entity = new UserEntity();

        entity.setCreatedAt(dto.getCreatedAt());
        entity.setEditedAt(dto.getEditedAt());
        entity.setName(dto.getName());
        entity.setLogin(dto.getLogin());
        entity.setPassword(hashPassword(dto.getPassword()));
        entity.setRole(dto.getRole());

        return entity;
    }

    public static List<UserDto> convertEntitiesToDto(List<UserEntity> entities) {
        if (entities == null) {
            return new ArrayList<>();
        }

        return entities.stream()
                .map(UserDtoConverter::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public static UserDto convertEntityToDto(UserEntity entity) {
        var dto = new UserDto();

        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setEditedAt(entity.getEditedAt());
        dto.setName(entity.getName());
        dto.setLogin(entity.getLogin());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole());
        dto.setCreatedTasks(TaskDtoConverter.convertEntitiesToDto(entity.getCreatedTasks()));
        dto.setAssignedTasks(TaskDtoConverter.convertEntitiesToDto(entity.getAssignedTasks()));
        dto.setComments(CommentDtoConverter.convertEntitiesToDto(entity.getComments()));

        return dto;
    }

    private static String hashPassword(String password) {
        return Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
    }
}
