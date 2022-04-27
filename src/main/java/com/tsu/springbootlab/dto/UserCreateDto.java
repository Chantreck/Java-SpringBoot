package com.tsu.springbootlab.dto;

import com.tsu.springbootlab.entity.Role;
import lombok.Data;

import java.util.Date;

@Data
public class UserCreateDto {
    private Date createdAt;
    private Date editedAt;
    private String name;
    private String login;
    private String password;
    private Role role;
}