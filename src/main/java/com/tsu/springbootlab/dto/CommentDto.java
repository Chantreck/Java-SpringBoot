package com.tsu.springbootlab.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CommentDto {
    private Integer id;
    private Date createdAt;
    private Date editedAt;
    private String user;
    private List<String> tasks;
    private String text;
}