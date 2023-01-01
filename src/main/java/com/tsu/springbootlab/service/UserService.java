package com.tsu.springbootlab.service;

import com.tsu.springbootlab.converters.UserConverter;
import com.tsu.springbootlab.csv.UserCSV;
import com.tsu.springbootlab.dto.UserCreateDto;
import com.tsu.springbootlab.dto.UserDto;
import com.tsu.springbootlab.dto.UserRegisterDto;
import com.tsu.springbootlab.entity.Role;
import com.tsu.springbootlab.entity.UserEntity;
import com.tsu.springbootlab.exceptions.IncorrectDateException;
import com.tsu.springbootlab.exceptions.UnknownFieldException;
import com.tsu.springbootlab.exceptions.UserNotFoundException;
import com.tsu.springbootlab.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;
    private final UserConverter converter;

    @Transactional
    public List<UserDto> createUsersFromCSV(List<UserCSV> users) {
        var result = new ArrayList<UserDto>();

        users.forEach(user -> {
            var userCreateDto = converter.convertCsvToDto(user);
            var userDto = createUser(userCreateDto);
            result.add(userDto);
        });

        return result;
    }

    @Transactional
    public UserDto createUser(UserCreateDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        var entity = converter.convertDtoToEntity(dto);
        entity = userRepository.save(entity);

        return converter.convertEntityToDto(entity);
    }

    @Transactional(readOnly = true)
    public UserDto getUserDtoById(Integer id) {
        var entity = getUserEntityById(id);
        return converter.convertEntityToDto(entity);
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

        return converter.convertEntitiesToDto(entities);
    }

    @Transactional
    public UserDto register(UserRegisterDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        var entity = converter.convertDtoToEntity(dto);
        entity = userRepository.save(entity);

        return converter.convertEntityToDto(entity);
    }

    public UserEntity getUserByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User " + login + " is not registered"));
    }

    @Transactional
    public UserDto changeRole(Integer userId, String role) {
        var entity = getUserEntityById(userId);
        entity.setRole(Role.valueOf(role));
        return converter.convertEntityToDto(entity);
    }

    @Transactional
    public void deleteUserById(Integer userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public UserDto updateUser(Integer userId, UserCreateDto dto) {
        var entity = getUserEntityById(userId);

        entity.setCreatedAt(dto.getCreatedAt());
        entity.setEditedAt(dto.getEditedAt());
        entity.setName(dto.getName());
        entity.setLogin(dto.getLogin());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setRole(dto.getRole());

        return converter.convertEntityToDto(entity);
    }
}
