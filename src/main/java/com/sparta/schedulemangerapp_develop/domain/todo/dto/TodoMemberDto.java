package com.sparta.schedulemangerapp_develop.domain.todo.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TodoMemberDto {
    private Long id;

    @NotNull(message = "Member ID는 필수입니다.")
    private Long memberId;

    @NotBlank(message = "할일 제목은 필수입니다.")
    private String title;

    @NotBlank(message = "유저명은 필수입니다.")
    private String username;

    @NotBlank(message = "이메일은 필수입니다.")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "이메일 형식이 유효하지 않습니다.")
    private String email;

    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
