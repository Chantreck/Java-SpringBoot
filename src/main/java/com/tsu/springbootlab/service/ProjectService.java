package com.tsu.springbootlab.service;

import com.tsu.springbootlab.converters.ProjectConverter;
import com.tsu.springbootlab.csv.ProjectCSV;
import com.tsu.springbootlab.dto.ProjectCreateDto;
import com.tsu.springbootlab.dto.ProjectDto;
import com.tsu.springbootlab.entity.ProjectEntity;
import com.tsu.springbootlab.exceptions.ProjectNotFoundException;
import com.tsu.springbootlab.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectConverter converter;

    @Transactional
    public ProjectDto createProject(ProjectCreateDto dto) {
        var entity = converter.convertDtoToEntity(dto);
        entity = projectRepository.save(entity);
        return converter.convertEntityToDto(entity);
    }

    @Transactional(readOnly = true)
    public ProjectDto getProjectDtoById(Integer id) {
        var entity = getProjectEntityById(id);
        return converter.convertEntityToDto(entity);
    }

    public ProjectEntity getProjectEntityById(Integer id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
    }

    @Transactional
    public List<ProjectDto> createProjectsFromCSV(List<ProjectCSV> projects) {
        var result = new ArrayList<ProjectDto>();

        projects.forEach(project -> {
            var projectCreateDto = converter.convertCsvToDto(project);
            var projectDto = createProject(projectCreateDto);
            result.add(projectDto);
        });

        return result;
    }

    @Transactional
    public void deleteProjectById(Integer projectId) {
        projectRepository.deleteById(projectId);
    }

    @Transactional
    public ProjectDto updateProject(Integer projectId, ProjectCreateDto dto) {
        var entity = getProjectEntityById(projectId);

        entity.setCreatedAt(dto.getCreatedAt());
        entity.setEditedAt(dto.getEditedAt());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());

        return converter.convertEntityToDto(entity);
    }
}
