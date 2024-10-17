package com.sparta.schedulemangerapp_develop.domain.todo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoRequestDto {
    @NotNull(message = "Member ID는 필수입니다.")
    private Long memberId; // Member 객체 대신 memberId를 받아 처리
    @NotNull(message = "할일 제목은 필수입니다.")
    @Size(max = 10, message = "할일 제목은 10자 이내여야 합니다.")
    private String title;
    @NotNull(message = "할일 내용은 필수입니다.")
    private String password;
    @NotNull(message = "비밀번호는 필수입니다.")
    private String description;
}
