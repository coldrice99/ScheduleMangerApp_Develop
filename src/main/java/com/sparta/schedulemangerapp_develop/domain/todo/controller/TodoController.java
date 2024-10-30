package com.sparta.schedulemangerapp_develop.domain.todo.controller;

import com.sparta.schedulemangerapp_develop.domain.todo.dto.TodoRequestDto;
import com.sparta.schedulemangerapp_develop.domain.todo.dto.TodoResponseDto;
import com.sparta.schedulemangerapp_develop.domain.todo.dto.TodoResponsePage;
import com.sparta.schedulemangerapp_develop.domain.todo.service.TodoService;
import com.sparta.schedulemangerapp_develop.domain.user.entity.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService todoService;

    //일정 생성하기
    @PostMapping()
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody @Valid TodoRequestDto requestDto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(todoService.createTodo(requestDto));
    }

    //전체 일정 조회
    @GetMapping()
    public ResponseEntity<TodoResponsePage> getTodoList(@RequestParam(required = false, defaultValue = "0") int page,
                                                        @RequestParam(required = false, defaultValue = "10") int size,
                                                        @RequestParam(required = false, defaultValue = "modifiedAt") String criteria){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(todoService.getTodoListWithPaging(page, size, criteria));
    }

    //선택 일정 조회
    @GetMapping("/{todoId}")
    public ResponseEntity<TodoResponseDto>getTodo(@PathVariable Long todoId){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(todoService.getTodo(todoId));
    }

    //선택 일정 수정
    @PutMapping("/{todoId}")
    public ResponseEntity<Void> updateTodo(
            @PathVariable Long todoId,
            @RequestBody TodoRequestDto requestDto,
            HttpServletRequest request
    ){
        Member member = (Member) request.getAttribute("member");
        if(member.isUser()){
            throw new IllegalArgumentException("ADMIN만 수정 가능합니다.");
        }
        todoService.updateTodo(todoId, requestDto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

    }

    //선택 일정 삭제
    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(
            @PathVariable Long todoId,
            HttpServletRequest request
    ){
        Member member = (Member) request.getAttribute("member");
        if(member.isUser()){
            throw new IllegalArgumentException("ADMIN만 삭제 가능합니다.");
        }
        todoService.deleteTodo(todoId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    //일정 담당자 배정
    @PostMapping("/{todoId}/assign/{memberId}")
    public ResponseEntity<Void> assignMemberToTodo(@PathVariable Long memberId, @PathVariable Long todoId){
        todoService.assignMember(memberId, todoId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
