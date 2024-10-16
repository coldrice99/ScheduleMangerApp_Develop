
package com.sparta.schedulemangerapp_develop.domain.todo.entity;

import com.sparta.schedulemangerapp_develop.domain.comment.entity.Comment;
import com.sparta.schedulemangerapp_develop.domain.member.entity.Member;
import com.sparta.schedulemangerapp_develop.domain.todo.dto.TodoRequestDto;
import com.sparta.schedulemangerapp_develop.domain.todo.dto.TodoResponseDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Getter
@Setter
@Table(name = "todo") // 매핑할 테이블의 이름을 지정
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자, private로 설정
public class Todo extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본가
    private Long id;
    // Member와의 다대일 관계 설정 (외래키)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    @Column(name = "title")
    private String title;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "description", nullable = false)
    private String description;

    // Member와의 연관관계 설정. 여러 명의 유저가 여러 개의 일정을 담당할 수 있음
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_todo",
            joinColumns = @JoinColumn(name = "todo_id"), // Todo 테이블의 외래키
            inverseJoinColumns = @JoinColumn(name = "user_id") // Member 테이블의 외래키
    )
    private List<Member> members;

    // Comment와의 연관관계 설정
    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;


    public void init(TodoRequestDto todoRequestDto, Member member) {
        this.member = member; // member 객체를 외부에서 받아서 설정
        this.title = todoRequestDto.getTitle();
        this.password = todoRequestDto.getPassword();
        this.description = todoRequestDto.getDescription();
    }

    public static Todo from(TodoRequestDto requestDto, Member member) {
        Todo todo = new Todo();
        todo.init(requestDto, member);
        return todo;
    }

    // Todo -> ResponseDto
    public TodoResponseDto to() {
        return new TodoResponseDto(
                this.id,
                this.member.getId(), // memberId 반환
                this.title,
                this.description,
                this.getCreatedAt(), // Timestamped 클래스에서 상속받은 createdAt, modifiedAt 반환
                this.getUpdatedAt(),
                0
        );
    }
    // 오버로딩
    public TodoResponseDto to(long commentCount) {
        return new TodoResponseDto(
                this.id,
                this.member.getId(), // memberId 반환
                this.title,
                this.description,
                this.getCreatedAt(), // Timestamped 클래스에서 상속받은 createdAt, modifiedAt 반환
                this.getUpdatedAt(),
                commentCount
        );
    }
    
}
