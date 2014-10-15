
# ADD task_user
 
# --- !Ups

INSERT INTO task_user(login) VALUES ('anonimo');
INSERT INTO task_user(login) VALUES ('miguel');
INSERT INTO task_user(login) VALUES ('pepe');

ALTER TABLE task ADD task_date timestamp;

# --- !Downs
DELETE FROM task;
DELETE FROM task_user;
ALTER TABLE task DROP task_date;