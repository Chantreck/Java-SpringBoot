package com.tsu.springbootlab.controller;

import com.tsu.springbootlab.dto.TaskCreateDto;
import com.tsu.springbootlab.dto.TaskDto;
import com.tsu.springbootlab.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public List<TaskDto> getTasks(@RequestParam(name = "project", required = false) Integer projectId,
                                  @RequestParam(name = "performer", required = false) Integer performerId) {
        if (projectId != null) {
            return taskService.getTasksByProjectId(projectId);
        }
        if (performerId != null) {
            return taskService.getTasksByPerformerId(performerId);
        }
        return taskService.getAllTasks();
    }

    @GetMapping("/fetch")
    public List<TaskDto> getTasksByCommentText(@RequestParam(name = "comment") String text) {
        return taskService.getTasksByCommentText(text);
    }

    @GetMapping("/search")
    public List<TaskDto> searchTasks(@RequestBody Map<String, String> dto) {
        return taskService.searchTasks(dto);
    }

    @GetMapping("/{taskId}")
    public TaskDto getTaskById(@PathVariable Integer taskId) {
        return taskService.getTaskDtoById(taskId);
    }

    @PostMapping
    public TaskDto createTask(@Validated @RequestBody TaskCreateDto dto) {
        return taskService.createTask(dto);
    }
}