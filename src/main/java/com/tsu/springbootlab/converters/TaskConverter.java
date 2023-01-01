package com.tsu.springbootlab.converters;

import com.tsu.springbootlab.csv.TaskCSV;
import com.tsu.springbootlab.dto.TaskCreateDto;
import com.tsu.springbootlab.dto.TaskDto;
import com.tsu.springbootlab.entity.ProjectEntity;
import com.tsu.springbootlab.entity.TaskEntity;
import com.tsu.springbootlab.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TaskConverter {
    private final ModelMapper modelMapper;

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(TaskCreateDto.class, TaskEntity.class)
                .addMappings(m -> m.skip(TaskCreateDto::getCreatorId, TaskEntity::setCreator))
                .addMappings(m -> m.skip(TaskCreateDto::getPerformerId, TaskEntity::setPerformer))
                .addMappings(m -> m.skip(TaskCreateDto::getProjectId, TaskEntity::setProject));

        modelMapper.createTypeMap(TaskEntity.class, TaskDto.class)
                .addMappings(m -> m.using(userEntityConverter()).map(TaskEntity::getCreator, TaskDto::setCreator))
                .addMappings(m -> m.using(userEntityConverter()).map(TaskEntity::getPerformer, TaskDto::setPerformer))
                .addMappings(m -> m.using(projectEntityConverter()).map(TaskEntity::getProject, TaskDto::setProject));
    }

    private Converter<UserEntity, String> userEntityConverter() {
        return context -> {
            var entity = context.getSource();
            return entity.getName();
        };
    }

    private Converter<ProjectEntity, String> projectEntityConverter() {
        return context -> {
            var entity = context.getSource();
            return entity.getName();
        };
    }

    public TaskEntity convertDtoToEntity(TaskCreateDto dto) {
        return modelMapper.map(dto, TaskEntity.class);
    }

    public TaskDto convertEntityToDto(TaskEntity entity) {
        return modelMapper.map(entity, TaskDto.class);
    }

    public List<TaskDto> convertEntitiesToDto(List<TaskEntity> entities) {
        if (entities == null) {
            return new ArrayList<>();
        }

        return entities.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public TaskCreateDto convertCsvToDto(TaskCSV csv) {
        return modelMapper.map(csv, TaskCreateDto.class);
    }
}
