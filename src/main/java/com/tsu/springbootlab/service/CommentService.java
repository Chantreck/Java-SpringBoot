package com.tsu.springbootlab.service;

import com.tsu.springbootlab.csv.CSVConverter;
import com.tsu.springbootlab.csv.CommentCSV;
import com.tsu.springbootlab.dto.CommentCreateDto;
import com.tsu.springbootlab.dto.CommentDto;
import com.tsu.springbootlab.dto.converters.CommentDtoConverter;
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

    @Transactional
    public CommentDto createComment(CommentCreateDto dto) {
        var author = userService.getUserEntityById(dto.getUserId());
        var tasks = taskService.getTasksByIds(dto.getTasks());

        var entity = CommentDtoConverter.convertDtoToEntity(dto, author, tasks);
        entity = commentRepository.save(entity);
        return CommentDtoConverter.convertEntityToDto(entity);
    }

    @Transactional(readOnly = true)
    public CommentDto getCommentDtoById(Integer id) {
        var entity = getCommentEntityById(id);
        return CommentDtoConverter.convertEntityToDto(entity);
    }

    public CommentEntity getCommentEntityById(Integer id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
    }

    @Transactional
    public List<CommentDto> createCommentsFromCSV(List<CommentCSV> comments) {
        var result = new ArrayList<CommentDto>();

        comments.forEach(comment -> {
            var commentCreateDto = CSVConverter.convertCommentCSV(comment);
            var commentDto = createComment(commentCreateDto);
            result.add(commentDto);
        });

        return result;
    }
}
