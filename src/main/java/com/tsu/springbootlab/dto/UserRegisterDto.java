package com.tsu.springbootlab.dto;

import lombok.Data;

@Data
public class UserRegisterDto {
    private String name;
    private String login;
    private String password;
}
