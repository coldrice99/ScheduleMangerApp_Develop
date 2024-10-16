package com.sparta.schedulemangerapp_develop.domain.member.entity;

import com.sparta.schedulemangerapp_develop.domain.comment.entity.Comment;
import com.sparta.schedulemangerapp_develop.domain.todo.entity.Timestamped;
import com.sparta.schedulemangerapp_develop.domain.todo.entity.Todo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "email", nullable = false)
    private String email;

    // Todo와의 연관관계 설정
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Todo> todos; // 하나의 Member가 여러 개의 Todo를 작성

    // Comment와의 연관관계 설정
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true) // 연관된 자식 엔티티를 자동으로 삭제
    private List<Comment> comments;


    public static Member from(ResultSet rs) throws SQLException {
        Member member = new Member();
        member.init(rs);
        return member;
    }

    private void init(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.username = rs.getString("username");
        this.email = rs.getString("email");
    }
}
