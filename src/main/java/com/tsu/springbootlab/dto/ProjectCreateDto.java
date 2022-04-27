package com.tsu.springbootlab.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class ProjectCreateDto {
    private Date createdAt;
    private Date editedAt;

    @NotBlank(message = "Name can't be empty or null")
    private String name;

    @NotBlank(message = "Description can't be empty or null")
    @Size(min = 10, message = "Description must contain 10 or more symbols")
    private String description;
}
