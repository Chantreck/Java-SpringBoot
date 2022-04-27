package com.tsu.springbootlab.controller;

import com.tsu.springbootlab.dto.CommentCreateDto;
import com.tsu.springbootlab.dto.CommentDto;
import com.tsu.springbootlab.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{commentId}")
    public CommentDto getCommentById(@PathVariable Integer commentId) {
        return commentService.getCommentDtoById(commentId);
    }

    @PostMapping
    public CommentDto createComment(@Validated @RequestBody CommentCreateDto dto) {
        return commentService.createComment(dto);
    }
}