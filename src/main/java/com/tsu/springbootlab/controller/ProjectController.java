package com.tsu.springbootlab.controller;

import com.tsu.springbootlab.dto.ProjectCreateDto;
import com.tsu.springbootlab.dto.ProjectDto;
import com.tsu.springbootlab.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/{projectId}")
    public ProjectDto getProjectById(@PathVariable Integer projectId) {
        return projectService.getProjectDtoById(projectId);
    }

    @PostMapping
    public ProjectDto createProject(@Validated @RequestBody ProjectCreateDto dto) {
        return projectService.createProject(dto);
    }

    @DeleteMapping("/{projectId}")
    public void deleteProjectById(@PathVariable Integer projectId) {
        projectService.deleteProjectById(projectId);
    }

    @PutMapping("/{projectId}")
    public ProjectDto updateProject(@PathVariable Integer projectId, @Validated @RequestBody ProjectCreateDto dto) {
        return projectService.updateProject(projectId, dto);
    }
}
