package com.tsu.springbootlab.service;

import com.tsu.springbootlab.converters.CommentConverter;
import com.tsu.springbootlab.csv.CommentCSV;
import com.tsu.springbootlab.dto.CommentCreateDto;
import com.tsu.springbootlab.dto.CommentDto;
import com.tsu.springbootlab.entity.CommentEntity;
import com.tsu.springbootlab.exceptions.CommentNotFoundException;
import com.tsu.springbootlab.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final TaskService taskService;
    private final CommentConverter converter;

    @Transactional
    public CommentDto createComment(CommentCreateDto dto) {
        var author = userService.getUserEntityById(dto.getUserId());
        var tasks = taskService.getTasksByIds(dto.getTasks());

        var entity = converter.convertDtoToEntity(dto);
        entity.setUser(author);
        entity.setTasks(tasks);
        entity = commentRepository.save(entity);

        return converter.convertEntityToDto(entity);
    }

    @Transactional(readOnly = true)
    public CommentDto getCommentDtoById(Integer id) {
        var entity = getCommentEntityById(id);
        return converter.convertEntityToDto(entity);
    }

    public CommentEntity getCommentEntityById(Integer id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
    }

    @Transactional
    public List<CommentDto> createCommentsFromCSV(List<CommentCSV> comments) {
        var result = new ArrayList<CommentDto>();

        comments.forEach(comment -> {
            var commentCreateDto = converter.convertCsvToDto(comment);
            var commentDto = createComment(commentCreateDto);
            result.add(commentDto);
        });

        return result;
    }

    @Transactional
    public void deleteCommentById(Integer commentId) {
        commentRepository.deleteById(commentId);
    }

    @Transactional
    public CommentDto updateComment(Integer commentId, CommentCreateDto dto) {
        var entity = getCommentEntityById(commentId);
        var author = userService.getUserEntityById(dto.getUserId());
        var tasks = taskService.getTasksByIds(dto.getTasks());

        entity.setCreatedAt(dto.getCreatedAt());
        entity.setEditedAt(dto.getEditedAt());
        entity.setUser(author);
        entity.setTasks(tasks);
        entity.setText(dto.getText());

        return converter.convertEntityToDto(entity);
    }
}
