
# ADD category
 
# --- !Ups

CREATE TABLE categoria(
   name varchar(25),
   PRIMARY KEY (name)
);

ALTER TABLE task ADD category varchar(25) DEFAULT 'descatalogado';
ALTER TABLE task ADD CONSTRAINT fk_task_category FOREIGN KEY (category) REFERENCES categoria (name);


INSERT INTO categoria(name) VALUES ('descatalogado');
INSERT INTO categoria(name) VALUES ('fruta');
INSERT INTO categoria(name) VALUES ('hortaliza');


# --- !Downs

DELETE FROM categoria;
ALTER TABLE task DROP category;
DROP TABLE IF EXIST categoria;