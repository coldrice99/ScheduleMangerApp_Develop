package com.sparta.schedulemangerapp_develop.domain.comment.repository;

import com.sparta.schedulemangerapp_develop.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 특정 일정의 댓글 조회
    Page<Comment> findByTodoId(Long todoId, Pageable pageable);

    // 특정 Todo에 대한 댓글 개수를 반환
    long countByTodoId(Long id);
}
