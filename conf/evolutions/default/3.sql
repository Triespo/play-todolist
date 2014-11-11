
# ADD category
 
# --- !Ups

CREATE SEQUENCE categoria_id_seq;

CREATE TABLE categoria(
   id integer NOT NULL DEFAULT nextval('categoria_id_seq'),
   name varchar(25),
   PRIMARY KEY (id)
);

ALTER TABLE task ADD category varchar(25) DEFAULT 'descatalogado';
ALTER TABLE task ADD CONSTRAINT fk_task_category FOREIGN KEY (category) REFERENCES categoria (name);
ALTER TABLE task_user ADD category varchar(25);
ALTER TABLE task_user ADD CONSTRAINT fk_task_user_categoria FOREIGN KEY (category) REFERENCES categoria(name);
ALTER TABLE categoria ADD user_name varchar(25);
ALTER TABLE categoria ADD CONSTRAINT fk_categoria_task_user FOREIGN KEY (user_name) REFERENCES task_user(login);


INSERT INTO categoria(name) VALUES ('descatalogado');
INSERT INTO categoria(name) VALUES ('fruta');
INSERT INTO categoria(name) VALUES ('hortaliza');


# --- !Downs

DELETE FROM categoria;
ALTER TABLE task DROP category;
DROP TABLE IF EXIST categoria;