package com.tsu.springbootlab.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
public class CommentCreateDto {
    private Date createdAt;
    private Date editedAt;
    private Integer userId;
    private List<Integer> tasks;

    @NotBlank(message = "Text can't be empty or null")
    private String text;
}