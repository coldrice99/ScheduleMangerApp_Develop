package com.sparta.schedulemangerapp_develop.domain.todo.controller;

import com.sparta.schedulemangerapp_develop.domain.todo.dto.TodoRequestDto;
import com.sparta.schedulemangerapp_develop.domain.todo.dto.TodoResponseDto;
import com.sparta.schedulemangerapp_develop.domain.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController //  이 클래스가 컨트롤러고 리스폰스 바디를 포함하고 있다.
@RequiredArgsConstructor // 필드 파라미터 생성자
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService todoService;

    // 일정 생성
    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto todoRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(todoService.createTodo(todoRequestDto));
    }

    // 전체 일정 조회
    @GetMapping
    public ResponseEntity<Page<TodoResponseDto>> getTodoList(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(todoService.getTodoListWithPaging(page, size));
    }

    // 선택 일정 조회
    @GetMapping("/{todoId}")
    public ResponseEntity<TodoResponseDto> getTodo(@PathVariable Long todoId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(todoService.getTodo(todoId));
    }

    // 선택 일정 수정
    @PutMapping("/{todoId}")
    public ResponseEntity<Void> updateTodo(
            @PathVariable Long todoId,
            @RequestBody TodoRequestDto requestDto) // 수정할 값과 비밀번호
    {
        todoService.updateTodo(todoId, requestDto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    // 선택 일정 삭제
    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(
            @PathVariable Long todoId,
            @RequestBody TodoRequestDto requestDto // 비밀번호
    ) {
        todoService.deleteTodo(todoId, requestDto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
