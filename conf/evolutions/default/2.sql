
# ADD task_user
 
# --- !Ups

CREATE TABLE task_user (
   login varchar(25) NOT NULL
);

ALTER TABLE task ADD CONSTRAINT fk_task_taskUser FOREIGN KEY (user_name) REFERENCES task_user (login);

INSERT INTO task_user VALUES ('anonimo');
INSERT INTO task_user VALUES ('miguel');
INSERT INTO task_user VALUES ('pepe');

ALTER TABLE task ADD task_date timestamp;

# --- !Downs

DROP TABLE task_user;