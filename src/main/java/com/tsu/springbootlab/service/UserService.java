package com.tsu.springbootlab.service;

import com.tsu.springbootlab.csv.CSVConverter;
import com.tsu.springbootlab.csv.UserCSV;
import com.tsu.springbootlab.dto.UserCreateDto;
import com.tsu.springbootlab.dto.UserDto;
import com.tsu.springbootlab.dto.converters.UserDtoConverter;
import com.tsu.springbootlab.entity.Role;
import com.tsu.springbootlab.entity.UserEntity;
import com.tsu.springbootlab.exceptions.IncorrectDateException;
import com.tsu.springbootlab.exceptions.UnknownFieldException;
import com.tsu.springbootlab.exceptions.UserNotFoundException;
import com.tsu.springbootlab.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public List<UserDto> createUsersFromCSV(List<UserCSV> users) {
        var result = new ArrayList<UserDto>();

        users.forEach(user -> {
            var userCreateDto = CSVConverter.convertUserCSV(user);
            var userDto = createUser(userCreateDto);
            result.add(userDto);
        });

        return result;
    }

    @Transactional
    public UserDto createUser(UserCreateDto dto) {
        var entity = UserDtoConverter.convertDtoToEntity(dto);
        entity = userRepository.save(entity);
        return UserDtoConverter.convertEntityToDto(entity);
    }

    @Transactional(readOnly = true)
    public UserDto getUserDtoById(Integer id) {
        var entity = getUserEntityById(id);
        return UserDtoConverter.convertEntityToDto(entity);
    }

    public UserEntity getUserEntityById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<UserDto> searchUsers(Map<String, String> dto) {
        var entities = userRepository.findAll((root, query, cb) -> {
            var predicates = new ArrayList<>();

            dto.forEach((field, value) -> {
                switch (field) {
                    case "createdAt":
                    case "editedAt":
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date converted = sdf.parse(value);
                            predicates.add(cb.equal(root.get(field), converted));
                        } catch (ParseException e) {
                            throw new IncorrectDateException(field);
                        }
                        break;
                    case "login":
                        predicates.add(cb.equal(root.get(field), value));
                        break;
                    case "name":
                        predicates.add(cb.like(root.get(field), '%' + value + '%'));
                        break;
                    case "role":
                        predicates.add(cb.equal(root.get(field), Role.valueOf(value)));
                        break;
                    default:
                        throw new UnknownFieldException(field);
                }
            });

            return cb.and(predicates.toArray(new Predicate[0]));
        });

        return UserDtoConverter.convertEntitiesToDto(entities);
    }
}
