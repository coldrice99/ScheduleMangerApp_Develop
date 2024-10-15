package com.sparta.schedulemangerapp_develop.domain.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TodoMemberDto {
    private Long id;
    private Long memberId;
    private String title;
    private String username;
    private String email;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
