
# ADD task_user
 
# --- !Ups
CREATE SEQUENCE task_user_id_seq;

CREATE TABLE task_user (
   id Integer NOT NULL DEFAULT nextval(task_user_id_seq)
   login varchar(25) NOT NULL DEFAULT 'anonimo',
   #UNIQUE (login)
);

alter table task add constraint fk_task_taskUser foreign key 
(user) references task_user (login) on delete restrict on update restrict;

# --- !Downs
DROP SEQUENCE task_user_id_seq;
DROP TABLE task_user;