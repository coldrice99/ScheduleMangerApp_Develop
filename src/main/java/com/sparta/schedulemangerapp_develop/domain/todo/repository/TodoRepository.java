package com.sparta.schedulemangerapp_develop.domain.todo.repository;

import com.sparta.schedulemangerapp_develop.domain.todo.dto.TodoMemberDto;
import com.sparta.schedulemangerapp_develop.domain.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo,Long> {
    List<TodoMemberDto> findAllWithPaging(int page, int size);
}