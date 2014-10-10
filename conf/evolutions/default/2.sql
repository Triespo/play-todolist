
# ADD task_user
 
# --- !Ups
CREATE TABLE task_user (
   login varchar(25) NOT NULL DEFAULT 'anonimo',
   #UNIQUE (login)
);

alter table task add constraint fk_task_taskUser foreign key 
(user) references task_user (login) on delete restrict on update restrict;

# --- !Downs

DROP TABLE task_user;