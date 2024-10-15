package com.sparta.schedulemangerapp_develop.domain.todo.service;

import com.sparta.schedulemangerapp_develop.domain.member.entity.Member;
import com.sparta.schedulemangerapp_develop.domain.member.repository.MemberRepository;
import com.sparta.schedulemangerapp_develop.domain.todo.dto.TodoMemberDto;
import com.sparta.schedulemangerapp_develop.domain.todo.dto.TodoRequestDto;
import com.sparta.schedulemangerapp_develop.domain.todo.dto.TodoResponseDto;
import com.sparta.schedulemangerapp_develop.domain.todo.entity.Todo;
import com.sparta.schedulemangerapp_develop.domain.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    // 일정 생성
    public TodoResponseDto createTodo(TodoRequestDto todoRequestDto) {
        // member 조회
        Member member = findmember(todoRequestDto.getMemberId());
        // Todo 생성 및 저장
        Todo todo = todoRepository.save(Todo.from(todoRequestDto, member)); // 리퀘스트 dto로 객체 생성 후 디비에 저장
        return todo.to();
    }

    // 일정 목록 조회
    public List<TodoResponseDto> getTodoList() {
        return todoRepository.findAll().stream().map(Todo::to).toList(); // Todo 엔티티가 TodoResponseDto로 변환된 결과를 리스트로 반환
    }

    // 일정 목록 페이징 조회
    public List<TodoMemberDto> getTodoListWithPaging(int page, int size) {
        return todoRepository.findAllWithPaging(page, size);
    }

    // 특정 일정 조회
    public TodoResponseDto getTodo(Long todoId) {
        Todo todo = findtodo(todoId);
        return todo.to();
    }

    // 일정 수정
    @Transactional
    public void updateTodo(Long todoId, TodoRequestDto requestDto) {
        Todo todo = findtodo(todoId);

        Member member = findmember(requestDto.getMemberId());

        // 수정 로직
        if (!Objects.equals(member.getId(), requestDto.getMemberId())) {
            throw new IllegalArgumentException("작성자만 수정 가능합니다.");
        }

        // 업데이트 후 저장
        todo.init(requestDto, member);
        todoRepository.save(todo);
    }

    // 일정 삭제
    public void deleteTodo(Long todoId, TodoRequestDto requestDto) {
        Todo todo = findtodo(todoId);
        Member member = findmember(requestDto.getMemberId());

        // 삭제 로직
        if (!Objects.equals(member.getId(), requestDto.getMemberId())) {
            throw new IllegalArgumentException("작성자만 삭제 가능합니다.");
        }

        todoRepository.deleteById(todoId);
    }

    private Todo findtodo(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 일정이 존재하지 않습니다."));
    }

    private Member findmember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
    }
}
