package com.tsu.springbootlab.controller;

import com.opencsv.bean.CsvToBeanBuilder;
import com.tsu.springbootlab.csv.CommentCSV;
import com.tsu.springbootlab.csv.ProjectCSV;
import com.tsu.springbootlab.csv.TaskCSV;
import com.tsu.springbootlab.csv.UserCSV;
import com.tsu.springbootlab.service.CommentService;
import com.tsu.springbootlab.service.ProjectService;
import com.tsu.springbootlab.service.TaskService;
import com.tsu.springbootlab.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RootController {

    private final UserService userService;
    private final TaskService taskService;
    private final ProjectService projectService;
    private final CommentService commentService;

    @PostMapping("/csv")
    public Map<String, List> createEntitiesFromCSV() {
        var result = new HashMap<String, List>();
        
        var filepath = Paths.get("src/main/resources/static/users.csv");
        try (BufferedReader br = Files.newBufferedReader(filepath, StandardCharsets.UTF_8)) {
            var users = parseCSV(br, UserCSV.class);
            result.put("users", userService.createUsersFromCSV(users));
        } catch (IOException e) {
            e.printStackTrace();
        }

        filepath = Paths.get("src/main/resources/static/projects.csv");
        try (BufferedReader br = Files.newBufferedReader(filepath, StandardCharsets.UTF_8)) {
            var projects = parseCSV(br, ProjectCSV.class);
            result.put("projects", projectService.createProjectsFromCSV(projects));
        } catch (IOException e) {
            e.printStackTrace();
        }

        filepath = Paths.get("src/main/resources/static/tasks.csv");
        try (BufferedReader br = Files.newBufferedReader(filepath, StandardCharsets.UTF_8)) {
            var tasks = parseCSV(br, TaskCSV.class);
            result.put("tasks", taskService.createTasksFromCSV(tasks));
        } catch (IOException e) {
            e.printStackTrace();
        }

        filepath = Paths.get("src/main/resources/static/comments.csv");
        try (BufferedReader br = Files.newBufferedReader(filepath, StandardCharsets.UTF_8)) {
            var comments = parseCSV(br, CommentCSV.class);
            result.put("comments", commentService.createCommentsFromCSV(comments));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private <T> List<T> parseCSV(BufferedReader br, Class<T> type) {
        return new CsvToBeanBuilder<T>(br)
                .withSeparator(',')
                .withType(type)
                .build()
                .parse();
    }
}