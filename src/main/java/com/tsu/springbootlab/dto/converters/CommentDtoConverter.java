package com.tsu.springbootlab.dto.converters;

import com.tsu.springbootlab.dto.CommentCreateDto;
import com.tsu.springbootlab.dto.CommentDto;
import com.tsu.springbootlab.entity.CommentEntity;
import com.tsu.springbootlab.entity.TaskEntity;
import com.tsu.springbootlab.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
public class CommentDto {
    private Integer id;
    private Date createdAt;
    private Date editedAt;
    private String user;
    private List<String> tasks;
    private String text;
}
 */

public class CommentDtoConverter {
    public static CommentEntity convertDtoToEntity(CommentCreateDto dto, UserEntity user, List<TaskEntity> tasks) {
        var entity = new CommentEntity();

        entity.setCreatedAt(dto.getCreatedAt());
        entity.setEditedAt(dto.getEditedAt());
        entity.setUser(user);
        entity.setTasks(tasks);
        entity.setText(dto.getText());

        return entity;
    }

    public static List<CommentDto> convertEntitiesToDto(List<CommentEntity> entities) {
        if (entities == null) {
            return new ArrayList<>();
        }

        return entities.stream()
                .map(CommentDtoConverter::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public static CommentDto convertEntityToDto(CommentEntity entity) {
        var dto = new CommentDto();

        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setEditedAt(entity.getEditedAt());
        dto.setUser(entity.getUser().getName());
        dto.setTasks(getTasksTitles(entity.getTasks()));
        dto.setText(entity.getText());

        return dto;
    }

    private static List<String> getTasksTitles(List<TaskEntity> tasks) {
        var result = new ArrayList<String>();

        if (tasks != null) {
            tasks.forEach(task -> result.add(task.getTitle()));
        }

        return result;
    }
}
