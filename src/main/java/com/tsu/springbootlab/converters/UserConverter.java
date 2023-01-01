package com.tsu.springbootlab.converters;

import com.tsu.springbootlab.csv.UserCSV;
import com.tsu.springbootlab.dto.UserCreateDto;
import com.tsu.springbootlab.dto.UserDto;
import com.tsu.springbootlab.dto.UserRegisterDto;
import com.tsu.springbootlab.entity.Role;
import com.tsu.springbootlab.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserConverter {
    private final ModelMapper modelMapper;

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(UserRegisterDto.class, UserEntity.class)
                .setPostConverter(registerDtoConverter());
    }

    private Converter<UserRegisterDto, UserEntity> registerDtoConverter() {
        return context -> {
            var entity = context.getDestination();
            var date = new Date(System.currentTimeMillis());

            entity.setCreatedAt(date);
            entity.setEditedAt(date);
            entity.setRole(Role.USER);

            return entity;
        };
    }

    public UserEntity convertDtoToEntity(UserCreateDto dto) {
        return modelMapper.map(dto, UserEntity.class);
    }

    public UserEntity convertDtoToEntity(UserRegisterDto dto) {
        return modelMapper.map(dto, UserEntity.class);
    }

    public UserDto convertEntityToDto(UserEntity entity) {
        return modelMapper.map(entity, UserDto.class);
    }

    public List<UserDto> convertEntitiesToDto(List<UserEntity> entities) {
        if (entities == null) {
            return new ArrayList<>();
        }

        return entities.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public UserCreateDto convertCsvToDto(UserCSV csv) {
        return modelMapper.map(csv, UserCreateDto.class);
    }
}
