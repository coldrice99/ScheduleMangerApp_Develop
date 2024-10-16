package com.sparta.schedulemangerapp_develop.domain.comment.entity;

import com.sparta.schedulemangerapp_develop.domain.comment.dto.CommentRequestDto;
import com.sparta.schedulemangerapp_develop.domain.comment.dto.CommentResponseDto;
import com.sparta.schedulemangerapp_develop.domain.member.entity.Member;
import com.sparta.schedulemangerapp_develop.domain.todo.dto.TodoRequestDto;
import com.sparta.schedulemangerapp_develop.domain.todo.entity.Timestamped;
import com.sparta.schedulemangerapp_develop.domain.todo.entity.Todo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String content; // 댓글 내용
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 댓글 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id", nullable = false)
    private Todo todo; // 댓글이 달린 일정

    public Comment(String content, Member member, Todo todo) {
        this.content = content;
        this.member = member;
        this.todo = todo;
    }

    public void init(CommentRequestDto commentRequestDto, Member member, Todo todo) {
        this.content = commentRequestDto.getContent();
        this.member = member;
        this.todo = todo;
    }

    // 새로운 Comment 객체 생성
    public static Comment from(CommentRequestDto requestDto, Member member, Todo todo) {
        Comment comment = new Comment();
        comment.init(requestDto, member, todo);
        return comment;
    }

    // Comment -> ResponseDto
    public CommentResponseDto to() {
        return new CommentResponseDto(
                this.getId(),
                this.getContent(),
                this.getMember().getUsername(),
                this.getCreatedAt(),
                this.getUpdatedAt()
        );
    }

    // 댓글 수정
    public void update(String content) {
        this.content = content;
    }
}
