
DROP TABLE IF EXISTS boat;
CREATE TABLE boat (id INT NOT NULL AUTO_INCREMENT,name VARCHAR(150) NOT NULL,description varchar(250) NOT NULL, PRIMARY KEY (id));

DROP TABLE IF EXISTS user_admin;
CREATE TABLE user_admin (id INT NOT NULL AUTO_INCREMENT, username VARCHAR(250) NOT NULL UNIQUE, password varchar(250) NOT NULL, role varchar(50) NOT NULL, PRIMARY KEY (id));
INSERT INTO user_admin (username, password, role)
VALUES ('admin', '$2a$10$DDIcubmSJteP1sbd14SLhuhiViOTGh2l6LQjkUMyk22rWTnjFNIAK', 'ADMIN'),
       ('morreti', '$2a$10$DDIcubmSJteP1sbd14SLhuhiViOTGh2l6LQjkUMyk22rWTnjFNIAK', 'ADMIN');

INSERT INTO boat (name, description)
VALUES ('Barge','Barge nowadays generally refers to a flat-bottomed inland waterway vessel which does not have its own means of mechanical propulsion'),
       ('Bracera', 'A bracera or brazzera  is a traditional Adriatic coastal cargo sailing vessel originated');