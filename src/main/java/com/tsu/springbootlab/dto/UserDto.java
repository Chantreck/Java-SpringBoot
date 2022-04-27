package com.tsu.springbootlab.dto;

import com.tsu.springbootlab.entity.Role;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserDto {
    private Integer id;
    private Date createdAt;
    private Date editedAt;
    private String name;
    private String login;
    private String password;
    private Role role;
    private List<TaskDto> createdTasks;
    private List<TaskDto> assignedTasks;
    private List<CommentDto> comments;
}