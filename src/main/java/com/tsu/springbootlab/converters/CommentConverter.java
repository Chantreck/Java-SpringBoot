package com.tsu.springbootlab.converters;

import com.tsu.springbootlab.csv.CommentCSV;
import com.tsu.springbootlab.dto.CommentCreateDto;
import com.tsu.springbootlab.dto.CommentDto;
import com.tsu.springbootlab.entity.CommentEntity;
import com.tsu.springbootlab.entity.TaskEntity;
import com.tsu.springbootlab.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommentConverter {
    private final ModelMapper modelMapper;

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(CommentEntity.class, CommentDto.class)
                .addMappings(m -> m.using(tasksConverter()).map(CommentEntity::getTasks, CommentDto::setTasks))
                .addMappings(m -> m.using(authorConverter()).map(CommentEntity::getUser, CommentDto::setUser));

        modelMapper.createTypeMap(CommentCSV.class, CommentCreateDto.class)
                .addMappings(m -> m.using(tasksParser()).map(CommentCSV::getTasks, CommentCreateDto::setTasks));
    }

    private Converter<UserEntity, String> authorConverter() {
        return context -> {
            var author = context.getSource();
            return author.getName();
        };
    }

    private Converter<List<TaskEntity>, List<String>> tasksConverter() {
        return context -> {
            var tasks = context.getSource();
            var result = new ArrayList<String>();

            if (tasks != null) {
                tasks.forEach(task -> result.add(task.getTitle()));
            }

            return result;
        };
    }

    private Converter<String, List<Integer>> tasksParser() {
        return context -> {
            var tasks = context.getSource();
            return Arrays.stream(tasks.split(" "))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        };
    }

    public CommentEntity convertDtoToEntity(CommentCreateDto dto) {
        return modelMapper.map(dto, CommentEntity.class);
    }

    public CommentDto convertEntityToDto(CommentEntity entity) {
        return modelMapper.map(entity, CommentDto.class);
    }

    public CommentCreateDto convertCsvToDto(CommentCSV csv) {
        return modelMapper.map(csv, CommentCreateDto.class);
    }
}
