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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final TodoRepository todoRepository;

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

    // 댓글 페이징 조회
    public Page<CommentResponseDto> getCommentsByTodoIdWihtPaging(Long todoId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> commentPage = commentRepository.findByTodoId(todoId, pageable);

        return commentPage.map(comment -> comment.to());
    }

    // 댓글 수정
    @Transactional
    public void updateComment(Long commentId, CommentRequestDto requestDto) {
        // 댓글 조회
        Comment comment = findComment(commentId);
        // 댓글 수정
        comment.update(requestDto.getContent());
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = findComment(commentId);
        // 댓글 삭제
        commentRepository.delete(comment);
    }

    private Todo findTodo(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));
    }

    private Member findMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
    }

    private Comment findComment(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
    }
}
