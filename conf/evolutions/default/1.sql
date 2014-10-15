# Tasks schema
 
# --- !Ups

CREATE SEQUENCE task_id_seq;
CREATE TABLE task (
    id integer NOT NULL DEFAULT nextval('task_id_seq'),
    label varchar(255)
);
CREATE TABLE task_user (
   login varchar(25) NOT NULL,
   PRIMARY KEY(login)
);

ALTER TABLE task ADD user_name varchar(25) DEFAULT 'anonimo';
ALTER TABLE task ADD CONSTRAINT fk_task_taskUser FOREIGN KEY (user_name) REFERENCES task_user (login);

# --- !Downs

ALTER TABLE task DROP user_name;
DROP TABLE IF EXISTS task_user;
DROP TABLE IF EXISTS task;
DROP SEQUENCE task_id_seq;