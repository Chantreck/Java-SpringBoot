package com.tsu.springbootlab.controller;

import com.opencsv.bean.CsvToBeanBuilder;
import com.tsu.springbootlab.csv.UserCSV;
import com.tsu.springbootlab.dto.UserCreateDto;
import com.tsu.springbootlab.dto.UserDto;
import com.tsu.springbootlab.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Integer userId) {
        return userService.getUserDtoById(userId);
    }

    @GetMapping("/search")
    public List<UserDto> searchUsers(@RequestBody Map<String, String> dto) {
        return userService.searchUsers(dto);
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserCreateDto dto) {
        return userService.createUser(dto);
    }

    @PostMapping("/csv")
    public List<UserDto> createUsersFromCSV() {
        var filepath = Paths.get("src/main/resources/static/users.csv");

        try (BufferedReader br = Files.newBufferedReader(filepath, StandardCharsets.UTF_8)) {
            var users = new CsvToBeanBuilder<UserCSV>(br)
                    .withSeparator(',')
                    .withType(UserCSV.class)
                    .build()
                    .parse();

            return userService.createUsersFromCSV(users);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
