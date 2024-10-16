package com.sparta.schedulemangerapp_develop.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private Long id; // 댓글 ID
    private String content; // 댓글 내용
    private String username; // 작성자 이름
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
