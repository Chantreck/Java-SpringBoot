package com.tsu.springbootlab.dto.converters;

import com.tsu.springbootlab.dto.ProjectCreateDto;
import com.tsu.springbootlab.dto.ProjectDto;
import com.tsu.springbootlab.entity.ProjectEntity;

public class ProjectDtoConverter {
    public static ProjectEntity convertDtoToEntity(ProjectCreateDto dto) {
        var entity = new ProjectEntity();

        entity.setCreatedAt(dto.getCreatedAt());
        entity.setEditedAt(dto.getEditedAt());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());

        return entity;
    }

    public static ProjectDto convertEntityToDto(ProjectEntity entity) {
        var dto = new ProjectDto();

        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setEditedAt(entity.getEditedAt());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setTasks(TaskDtoConverter.convertEntitiesToDto(entity.getTasks()));

        return dto;
    }
}
