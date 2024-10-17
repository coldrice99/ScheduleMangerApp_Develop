
// CommentController.java
package com.sparta.schedulemangerapp_develop.domain.comment.controller;

import com.sparta.schedulemangerapp_develop.domain.comment.dto.CommentRequestDto;
import com.sparta.schedulemangerapp_develop.domain.comment.dto.CommentResponseDto;
import com.sparta.schedulemangerapp_develop.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 생성
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@Valid @RequestBody CommentRequestDto commentRequestDto) {
        // 디버깅 로그 추가
        System.out.println("Received todoId: " + commentRequestDto.getTodoId());
        System.out.println("Received memberId: " + commentRequestDto.getMemberId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.createComment(commentRequestDto));
    }

    // 특정 일정의 댓글 조회
    @GetMapping("/{todoId}")
    public ResponseEntity<Page<CommentResponseDto>> getCommentByTodoIdWithPaging(
            @PathVariable Long todoId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.getCommentsByTodoIdWihtPaging(todoId,page,size));
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable Long commentId,
                                              @RequestBody CommentRequestDto requestDto) {
        commentService.updateComment(commentId, requestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto) {
        commentService.deleteComment(commentId, requestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
