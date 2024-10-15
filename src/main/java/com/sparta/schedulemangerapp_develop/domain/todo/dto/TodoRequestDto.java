package com.sparta.schedulemangerapp_develop.domain.todo.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoRequestDto {
    private Long memberId; // Member 객체 대신 memberId를 받아 처리
    private String title;
    private String password;
    private String description;
}
