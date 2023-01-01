package com.tsu.springbootlab.service;

import com.tsu.springbootlab.converters.TaskConverter;
import com.tsu.springbootlab.csv.TaskCSV;
import com.tsu.springbootlab.dto.TaskCreateDto;
import com.tsu.springbootlab.dto.TaskDto;
import com.tsu.springbootlab.entity.TaskEntity;
import com.tsu.springbootlab.exceptions.IncorrectDateException;
import com.tsu.springbootlab.exceptions.TaskNotFoundException;
import com.tsu.springbootlab.exceptions.UnknownFieldException;
import com.tsu.springbootlab.repository.CommentRepository;
import com.tsu.springbootlab.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;

    private final ProjectService projectService;
    private final UserService userService;

    private final TaskConverter converter;

    @Transactional
    public TaskDto createTask(TaskCreateDto dto) {
        var creator = userService.getUserEntityById(dto.getCreatorId());
        var performer = userService.getUserEntityById(dto.getPerformerId());
        var project = projectService.getProjectEntityById(dto.getProjectId());

        var entity = converter.convertDtoToEntity(dto);
        entity.setCreator(creator);
        entity.setPerformer(performer);
        entity.setProject(project);
        entity = taskRepository.save(entity);

        return converter.convertEntityToDto(entity);
    }

    @Transactional(readOnly = true)
    public TaskDto getTaskDtoById(Integer id) {
        var entity = getTaskEntityById(id);
        return converter.convertEntityToDto(entity);
    }

    public TaskEntity getTaskEntityById(Integer id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public List<TaskEntity> getTasksByIds(List<Integer> ids) {
        return taskRepository.findAllById(ids);
    }

    @Transactional(readOnly = true)
    public List<TaskDto> getTasksByProjectId(Integer projectId) {
        var project = projectService.getProjectEntityById(projectId);
        var entities = taskRepository.findAllByProject(project);
        return converter.convertEntitiesToDto(entities);
    }

    @Transactional(readOnly = true)
    public List<TaskDto> getTasksByPerformerId(Integer performerId) {
        var performer = userService.getUserEntityById(performerId);
        var entities = taskRepository.findAllByPerformer(performer);
        return converter.convertEntitiesToDto(entities);
    }

    @Transactional(readOnly = true)
    public List<TaskDto> getAllTasks() {
        var entities = taskRepository.findAll();
        return converter.convertEntitiesToDto(entities);
    }

    @Transactional(readOnly = true)
    public List<TaskDto> getTasksByCommentText(String text) {
        var comments = commentRepository.findAllByTextContaining(text);

        var taskEntities = new HashSet<TaskEntity>();
        comments.forEach(comment -> taskEntities.addAll(comment.getTasks()));

        return taskEntities.stream()
                .map(converter::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TaskDto> searchTasks(Map<String, String> dto) {
        var entities = taskRepository.findAll((root, query, sb) -> {
            var predicates = new ArrayList<>();

            dto.forEach((field, value) -> {
                switch (field) {
                    case "createdAt":
                    case "editedAt":
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date converted = sdf.parse(value);
                            predicates.add(sb.equal(root.get(field), converted));
                        } catch (ParseException e) {
                            throw new IncorrectDateException(field);
                        }
                        break;
                    case "title":
                    case "description":
                    case "priority":
                        predicates.add(sb.like(root.get(field), '%' + value + '%'));
                        break;
                    case "project":
                    case "creator":
                    case "performer":
                        predicates.add(sb.equal(root.get(field).get("id"), value));
                        break;
                    default:
                        throw new UnknownFieldException(field);
                }
            });

            return sb.and(predicates.toArray(new Predicate[0]));
        });

        return converter.convertEntitiesToDto(entities);
    }

    @Transactional
    public List<TaskDto> createTasksFromCSV(List<TaskCSV> tasks) {
        var result = new ArrayList<TaskDto>();

        tasks.forEach(task -> {
            var taskCreateDto = converter.convertCsvToDto(task);
            var taskDto = createTask(taskCreateDto);
            result.add(taskDto);
        });

        return result;
    }

    @Transactional
    public void deleteTaskById(Integer taskId) {
        taskRepository.deleteById(taskId);
    }

    @Transactional
    public TaskDto updateTask(Integer taskId, TaskCreateDto dto) {
        var entity = getTaskEntityById(taskId);
        var creator = userService.getUserEntityById(dto.getCreatorId());
        var performer = userService.getUserEntityById(dto.getPerformerId());
        var project = projectService.getProjectEntityById(dto.getProjectId());

        entity.setCreatedAt(dto.getCreatedAt());
        entity.setEditedAt(dto.getEditedAt());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setCreator(creator);
        entity.setPerformer(performer);
        entity.setPriority(dto.getPriority());
        entity.setProject(project);
        entity.setTaskETA(dto.getTaskETA());

        return converter.convertEntityToDto(entity);
    }

    @Transactional
    public TaskDto changeTaskPriority(Integer taskId, String priority) {
        var entity = getTaskEntityById(taskId);

        entity.setPriority(priority);

        return converter.convertEntityToDto(entity);
    }

    @Transactional
    public TaskDto changeTaskPerformer(Integer taskId, String performerLogin) {
        var entity = getTaskEntityById(taskId);
        var performer = userService.getUserByLogin(performerLogin);

        entity.setPerformer(performer);

        return converter.convertEntityToDto(entity);
    }
}
