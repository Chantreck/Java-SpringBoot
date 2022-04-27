package com.tsu.springbootlab.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProjectDto {
    private Integer id;
    private Date createdAt;
    private Date editedAt;
    private String name;
    private String description;
    private List<TaskDto> tasks;
}