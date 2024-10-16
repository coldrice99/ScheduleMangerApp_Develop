package com.sparta.schedulemangerapp_develop.domain.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
    private String content; // 댓글 내용
    private Long memberId;  // 댓글 작성자 ID
    private Long todotId;   // 댓글이 달린 일정의 ID
}
