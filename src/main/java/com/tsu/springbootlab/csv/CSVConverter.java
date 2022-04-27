package com.tsu.springbootlab.csv;

import com.tsu.springbootlab.dto.CommentCreateDto;
import com.tsu.springbootlab.dto.ProjectCreateDto;
import com.tsu.springbootlab.dto.TaskCreateDto;
import com.tsu.springbootlab.dto.UserCreateDto;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CSVConverter {
    public static UserCreateDto convertUserCSV(UserCSV csv) {
        var dto = new UserCreateDto();

        dto.setCreatedAt(csv.getCreatedAt());
        dto.setEditedAt(csv.getEditedAt());
        dto.setLogin(csv.getLogin());
        dto.setName(csv.getName());
        dto.setPassword(csv.getPassword());
        dto.setRole(csv.getRole());

        return dto;
    }

    public static ProjectCreateDto convertProjectCSV(ProjectCSV csv) {
        var dto = new ProjectCreateDto();

        dto.setCreatedAt(csv.getCreatedAt());
        dto.setEditedAt(csv.getEditedAt());
        dto.setName(csv.getName());
        dto.setDescription(csv.getDescription());

        return dto;
    }

    public static TaskCreateDto convertTaskCSV(TaskCSV csv) {
        var dto = new TaskCreateDto();

        dto.setCreatedAt(csv.getCreatedAt());
        dto.setEditedAt(csv.getEditedAt());
        dto.setTitle(csv.getTitle());
        dto.setDescription(csv.getDescription());
        dto.setCreatorId(csv.getCreatorId());
        dto.setPerformerId(csv.getPerformerId());
        dto.setPriority(csv.getPriority());
        dto.setProjectId(csv.getProjectId());
        dto.setTaskETA(csv.getTaskETA());

        return dto;
    }

    public static CommentCreateDto convertCommentCSV(CommentCSV csv) {
        var dto = new CommentCreateDto();

        dto.setCreatedAt(csv.getCreatedAt());
        dto.setEditedAt(csv.getEditedAt());
        dto.setUserId(csv.getUserId());
        dto.setTasks(Arrays
                .stream(csv.getTasks().split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList()));
        dto.setText(csv.getText());

        return dto;
    }
}
