package com.tsu.springbootlab.converters;

import com.tsu.springbootlab.csv.ProjectCSV;
import com.tsu.springbootlab.dto.ProjectCreateDto;
import com.tsu.springbootlab.dto.ProjectDto;
import com.tsu.springbootlab.entity.ProjectEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectConverter {
    private final ModelMapper modelMapper;

    public ProjectEntity convertDtoToEntity(ProjectCreateDto dto) {
        return modelMapper.map(dto, ProjectEntity.class);
    }

    public ProjectDto convertEntityToDto(ProjectEntity entity) {
        return modelMapper.map(entity, ProjectDto.class);
    }

    public ProjectCreateDto convertCsvToDto(ProjectCSV csv) {
        return modelMapper.map(csv, ProjectCreateDto.class);
    }
}
