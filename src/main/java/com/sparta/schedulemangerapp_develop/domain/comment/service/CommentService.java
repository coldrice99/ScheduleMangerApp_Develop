package com.sparta.schedulemangerapp_develop.domain.comment.service;

import com.sparta.schedulemangerapp_develop.domain.comment.dto.CommentRequestDto;
import com.sparta.schedulemangerapp_develop.domain.comment.dto.CommentResponseDto;
import com.sparta.schedulemangerapp_develop.domain.comment.entity.Comment;
import com.sparta.schedulemangerapp_develop.domain.comment.repository.CommentRepository;
import com.sparta.schedulemangerapp_develop.domain.member.entity.Member;
import com.sparta.schedulemangerapp_develop.domain.member.repository.MemberRepository;
import com.sparta.schedulemangerapp_develop.domain.todo.entity.Todo;
import com.sparta.schedulemangerapp_develop.domain.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private CommentRepository commentRepository;
    private MemberRepository memberRepository;
    private TodoRepository todoRepository;

    // 댓글 생성
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {
        // 유저 조회
        Member member = findMember(commentRequestDto.getMemberId());
        // 일정 조회
        Todo todo = findTodo(commentRequestDto.getTodotId());
        // 댓글 생성 및 저장
        Comment comment = commentRepository.save(Comment.from(commentRequestDto,member,todo));
        return comment.to();
    }

    public List<CommentResponseDto> getCommentsByTodoIdWihtPaging(Long todoId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> commentPage = commentRepository.findByTodoId(todoId, pageable);

        return commentPage.getContent().stream()
                .map(Comment::to)
                .collect(Collectors.toList());
    }


    private Todo findTodo(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));
    }

    private Member findMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
    }
}
