
# ADD task_user
 
# --- !Ups

INSERT INTO task_user VALUES ('anonimo');
INSERT INTO task_user VALUES ('miguel');
INSERT INTO task_user VALUES ('pepe');

ALTER TABLE task ADD task_date timestamp;

# --- !Downs

DROP TABLE task_user;