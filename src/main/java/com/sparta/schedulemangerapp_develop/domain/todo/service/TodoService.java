
package com.sparta.schedulemangerapp_develop.domain.todo.service;

import com.sparta.schedulemangerapp_develop.domain.comment.repository.CommentRepository;
import com.sparta.schedulemangerapp_develop.domain.member.entity.Member;
import com.sparta.schedulemangerapp_develop.domain.member.repository.MemberRepository;
import com.sparta.schedulemangerapp_develop.domain.todo.dto.TodoMemberDto;
import com.sparta.schedulemangerapp_develop.domain.todo.dto.TodoRequestDto;
import com.sparta.schedulemangerapp_develop.domain.todo.dto.TodoResponseDto;
import com.sparta.schedulemangerapp_develop.domain.todo.entity.Todo;
import com.sparta.schedulemangerapp_develop.domain.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    // 일정 생성
    public TodoResponseDto createTodo(TodoRequestDto todoRequestDto) {
        // member 조회
        Member member = findMember(todoRequestDto.getMemberId());
        // Todo 생성 및 저장
        Todo todo = todoRepository.save(Todo.from(todoRequestDto, member)); // 리퀘스트 dto로 객체 생성 후 디비에 저장
        return todo.to();
    }

//    // 전체 일정 조회
//    public List<TodoResponseDto> getTodoList() {
//        return todoRepository.findAll().stream().map(Todo::to).toList(); // Todo 엔티티가 TodoResponseDto로 변환된 결과를 리스트로 반환
//    }

    // 전체 일정 페이징 조회
    public Page<TodoResponseDto> getTodoListWithPaging(int page, int size) {
        // 수정일(updatedAt)을 기준으로 내림차순 정렬하는 Pageable 객체 생성
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updatedAt"));
        // 페이징된 todo 목록 조회
        Page<Todo> todoPage = todoRepository.findAll(pageable);

        return todoPage.map(todo -> {
            long commentCount = commentRepository.countByTodoId(todo.getId());
            return new TodoResponseDto(
                    todo.getId(),
                    todo.getMember().getId(),
                    todo.getTitle(),
                    todo.getDescription(),
                    todo.getCreatedAt(),
                    todo.getUpdatedAt(),
                    commentCount // 댓글 개수를 추가하여 반환
            );
        });
    }
    // 특정 일정 조회
    public TodoResponseDto getTodo(Long todoId) {
        Todo todo = findTodo(todoId);
        return todo.to();
    }

    // 일정 수정
    @Transactional // 변경 감지
    public void updateTodo(Long todoId, TodoRequestDto requestDto) {
        Todo todo = findTodo(todoId);

        memberValidation(todo.getMember().getId(), requestDto.getMemberId());
        passwordValidation(todo.getPassword(), requestDto.getPassword());

        // 비밀번호 검증을 통과한 후에만 업데이트
        Member member = findMember(requestDto.getMemberId());
        // 업데이트 후 저장
        todo.init(requestDto, member);
//        todoRepository.save(todo); // 수정 내용 반영, 트랜잭션이 끝나면 자동으로 반영됨 (save() 불필요)
    }

    // 일정 삭제
    @Transactional
    public void deleteTodo(Long todoId, TodoRequestDto requestDto) {
        Todo todo = findTodo(todoId);
        Member member = findMember(requestDto.getMemberId());

        memberValidation(todo.getMember().getId(), requestDto.getMemberId());
        passwordValidation(todo.getPassword(), requestDto.getPassword());

        todoRepository.deleteById(todoId);
    }

    private Todo findTodo(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 일정이 존재하지 않습니다."));
    }

    private Member findMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
    }

    // 비밀번호 검증
    private void passwordValidation(String storedPassword, String inputPassword ) {
        if (storedPassword == null || inputPassword == null) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }
        if(!storedPassword.equals(inputPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    // 작성자 검증
    private void memberValidation(Long storedMemberId, Long inputMemberId) {
        if (!Objects.equals(storedMemberId, inputMemberId)) {
            throw new IllegalArgumentException("작성자만 접근 가능합니다.");
        }
    }
}
