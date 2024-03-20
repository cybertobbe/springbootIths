CREATE TABLE user (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      fullName VARCHAR(255),
                      loginName VARCHAR(255),
                      mail VARCHAR(255),
                      password VARCHAR(255),
                      admin TINYINT(1) DEFAULT 0
);

CREATE TABLE message (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         title VARCHAR(255),
                         chatMessage TEXT,
                         author VARCHAR(255),
                         visible TINYINT(1) DEFAULT 0,
                         user_id BIGINT,
                         date DATE,
                         FOREIGN KEY (user_id) REFERENCES user(id)
);

insert into user(full_name, admin) VALUES ('John Doe', 1);
insert into message(title, chat_message, person_id, visible) VALUES ('Hej','Vi jobbar', 1,1);

