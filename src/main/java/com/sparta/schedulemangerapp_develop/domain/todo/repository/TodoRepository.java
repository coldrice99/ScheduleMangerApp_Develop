package com.sparta.schedulemangerapp_develop.domain.todo.repository;

import com.sparta.schedulemangerapp_develop.domain.todo.dto.TodoMemberDto;
import com.sparta.schedulemangerapp_develop.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo,Long> {
    // Pageable을 사용하여 페이징 처리
    Page<Todo> findAll(Pageable pageable);
}