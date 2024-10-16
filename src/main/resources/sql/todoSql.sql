-- 테이블 삭제 (기존 테이블이 있을 경우 삭제)
DROP TABLE IF EXISTS user_todo;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS todo;
DROP TABLE IF EXISTS member;

-- member 테이블 생성
CREATE TABLE member
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(255),
    email      VARCHAR(255),
    created_at VARCHAR(10),
    updated_at VARCHAR(10)
);

-- todo 테이블 생성
CREATE TABLE todo
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(255),
    description VARCHAR(255),
    password    VARCHAR(255),
    creator_id  BIGINT,  -- 작성자 외래키
    created_at  VARCHAR(10),
    updated_at  VARCHAR(10),
    FOREIGN KEY (creator_id) REFERENCES member (id)  -- 작성자와의 외래 키 관계 (다대일)
);

-- comment 테이블 생성
CREATE TABLE comment
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    content    VARCHAR(255),
    member_id  BIGINT NOT NULL,  -- 댓글 작성자 외래 키
    todo_id    BIGINT NOT NULL,  -- 댓글이 달린 일정 외래 키
    created_at VARCHAR(10),
    updated_at VARCHAR(10),
    FOREIGN KEY (member_id) REFERENCES member (id),   -- Member와의 외래 키 관계
    FOREIGN KEY (todo_id) REFERENCES todo (id) ON DELETE CASCADE  -- Todo 삭제 시 댓글도 삭제
);

-- user_todo (유저와 일정 간의 N:M 관계를 위한 중간 테이블) 생성
CREATE TABLE user_todo
(
    user_id BIGINT NOT NULL,
    todo_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, todo_id),
    FOREIGN KEY (user_id) REFERENCES member (id),
    FOREIGN KEY (todo_id) REFERENCES todo (id)
);

-- 유저 추가 쿼리
INSERT INTO member (username, email, created_at, updated_at)
VALUES ('김유저', 'user@example.com', '2024-10-03', '2024-10-04');

INSERT INTO member (username, email, created_at, updated_at)
VALUES ('이유저', 'user2@example.com', '2024-10-03', '2024-10-04');
