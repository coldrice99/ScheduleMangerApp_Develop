package com.sparta.schedulemangerapp_develop.domain.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
    @NotNull(message = "댓글 내용은 필수입니다.")
    @Size(min = 1, max = 255, message = "댓글 내용은 1자 이상 255자 이내여야 합니다.")
    private String content; // 댓글 내용

    @NotNull(message = "Member ID는 필수입니다.")
    private Long memberId;  // 댓글 작성자 ID

    @NotNull(message = "Todo ID는 필수입니다.")
    private Long todoId;   // 댓글이 달린 일정의 ID
}
