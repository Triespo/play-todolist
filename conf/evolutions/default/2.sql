
# ADD task_user
 
# --- !Ups

CREATE TABLE task_user (
   login varchar(25) NOT NULL DEFAULT 'anonimo'
);

alter table task add constraint fk_task_taskUser foreign key 
(user) references task_user (login) on delete restrict on update restrict;

# --- !Downs

DROP TABLE task;
DROP SEQUENCE task_id_seq;
DROP TABLE task_user;