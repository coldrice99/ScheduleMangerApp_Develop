
package com.sparta.schedulemangerapp_develop.domain.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class TodoResponseDto {
    private Long id;
    private String memberName;
    private String title;
    private String description;
    private List<CommentResponseDto> comments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String weather;
}
