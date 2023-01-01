package com.tsu.springbootlab.controller;

import com.tsu.springbootlab.dto.LoginDto;
import com.tsu.springbootlab.dto.UserDto;
import com.tsu.springbootlab.dto.UserRegisterDto;
import com.tsu.springbootlab.entity.Role;
import com.tsu.springbootlab.exceptions.LoginFailedException;
import com.tsu.springbootlab.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public UserDto register(@RequestBody UserRegisterDto dto) {
        return userService.register(dto);
    }

    @PostMapping("/login")
    public Role login(@RequestBody LoginDto dto) {
        var login = dto.getLogin();
        var user = userService.getUserByLogin(login);

        if (BCrypt.checkpw(dto.getPassword(), user.getPassword())) {
            return user.getRole();
        } else {
            throw new LoginFailedException();
        }
    }
}
