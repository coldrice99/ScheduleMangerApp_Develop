package com.sparta.schedulemangerapp_develop.domain.todo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class TodoRequestDto {
    @NotNull
    private Long memberId;
    @Size(min = 2, max = 10, message = "2글자 이상, 10글자 이하만 가능합니다.")
    private String title;
    @Size(min = 2, max = 10, message = "2글자 이상, 10글자 이하만 가능합니다.")
    private String description;
}
