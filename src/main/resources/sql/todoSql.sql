DROP TABLE IF EXISTS todo;
DROP TABLE IF EXISTS member;

#일반 ver
CREATE TABLE todo (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      title VARCHAR(255),
                      username VARCHAR(255),
                      description VARCHAR(255),
                      password VARCHAR(255),
                      created_at VARCHAR(10),
                      updated_at VARCHAR(10)
);


#외래키 ver
CREATE TABLE todo (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      title VARCHAR(255),
                      description VARCHAR(255),
                      password VARCHAR(255),
                      member_id BIGINT,
                      created_at VARCHAR(10),
                      updated_at VARCHAR(10),
                      FOREIGN KEY (member_id) REFERENCES member(id)
);

CREATE TABLE member (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        username VARCHAR(255),
                        email VARCHAR(255),
                        created_at VARCHAR(10),
                        updated_at VARCHAR(10)
);