package com.sparta.schedulemangerapp_develop.domain.comment.controller;

import com.sparta.schedulemangerapp_develop.domain.comment.dto.CommentRequestDto;
import com.sparta.schedulemangerapp_develop.domain.comment.dto.CommentResponseDto;
import com.sparta.schedulemangerapp_develop.domain.comment.entity.Comment;
import com.sparta.schedulemangerapp_develop.domain.comment.repository.CommentRepository;
import com.sparta.schedulemangerapp_develop.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 생성
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.createComment(commentRequestDto));
    }

    // 특정 일정의 댓글 조회
    @GetMapping("/{todoId}")
    public ResponseEntity<List<CommentResponseDto>> getCommentByTodoIdWithPaging(
            @PathVariable Long todoId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.getCommentsByTodoIdWihtPaging(todoId,page,size));
    }

    //

}
