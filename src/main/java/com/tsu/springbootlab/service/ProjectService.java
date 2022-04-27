package com.tsu.springbootlab.service;

import com.tsu.springbootlab.csv.CSVConverter;
import com.tsu.springbootlab.csv.ProjectCSV;
import com.tsu.springbootlab.dto.ProjectCreateDto;
import com.tsu.springbootlab.dto.ProjectDto;
import com.tsu.springbootlab.dto.converters.ProjectDtoConverter;
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

    @Transactional
    public ProjectDto createProject(ProjectCreateDto dto) {
        var entity = ProjectDtoConverter.convertDtoToEntity(dto);
        entity = projectRepository.save(entity);
        return ProjectDtoConverter.convertEntityToDto(entity);
    }

    @Transactional(readOnly = true)
    public ProjectDto getProjectDtoById(Integer id) {
        var entity = getProjectEntityById(id);
        return ProjectDtoConverter.convertEntityToDto(entity);
    }

    public ProjectEntity getProjectEntityById(Integer id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
    }

    @Transactional
    public List<ProjectDto> createProjectsFromCSV(List<ProjectCSV> projects) {
        var result = new ArrayList<ProjectDto>();

        projects.forEach(project -> {
            var projectCreateDto = CSVConverter.convertProjectCSV(project);
            var projectDto = createProject(projectCreateDto);
            result.add(projectDto);
        });

        return result;
    }
}
