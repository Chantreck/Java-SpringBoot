package com.tsu.springbootlab.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
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