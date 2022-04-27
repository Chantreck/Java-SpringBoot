package com.tsu.springbootlab.dto.converters;

import com.tsu.springbootlab.dto.TaskCreateDto;
import com.tsu.springbootlab.dto.TaskDto;
import com.tsu.springbootlab.entity.ProjectEntity;
import com.tsu.springbootlab.entity.TaskEntity;
import com.tsu.springbootlab.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
public class TaskDto {
    private Integer id;
    private Date createdAt;
    private Date editedAt;
    private String title;
    private String description;
    private String creator;
    private String performer;
    private String priority;
    private String project;
    private Integer taskETA;
    private List<CommentDto> comments;
}
 */

public class TaskDtoConverter {
    public static TaskEntity convertDtoToEntity(TaskCreateDto dto, UserEntity creator, UserEntity performer, ProjectEntity project) {
        var entity = new TaskEntity();

        entity.setCreatedAt(dto.getCreatedAt());
        entity.setEditedAt(dto.getEditedAt());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setCreator(creator);
        entity.setPerformer(performer);
        entity.setPriority(dto.getPriority());
        entity.setProject(project);
        entity.setTaskETA(dto.getTaskETA());

        return entity;
    }

    public static List<TaskDto> convertEntitiesToDto(List<TaskEntity> entities) {
        if (entities == null) {
            return new ArrayList<>();
        }

        return entities.stream()
                .map(TaskDtoConverter::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public static TaskDto convertEntityToDto(TaskEntity entity) {
        var dto = new TaskDto();

        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setEditedAt(entity.getEditedAt());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setCreator(entity.getCreator().getName());
        dto.setPerformer(entity.getPerformer().getName());
        dto.setPriority(entity.getPriority());
        dto.setProject(entity.getProject().getName());
        dto.setTaskETA(entity.getTaskETA());
        dto.setComments(CommentDtoConverter.convertEntitiesToDto(entity.getComments()));

        return dto;
    }
}
