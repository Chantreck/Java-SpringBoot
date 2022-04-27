package com.tsu.springbootlab.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class TaskCreateDto {
    private Date createdAt;
    private Date editedAt;

    @NotBlank(message = "Title can't be empty or null")
    private String title;

    private String description;
    private Integer creatorId;
    private Integer performerId;
    private String priority;
    private Integer projectId;
    private Integer taskETA;
}