-- sample data for unit testing
INSERT INTO users(id, username, email, password)
VALUES('thowl', 'prog3@th-owl.de', 'start') ;
INSERT INTO users(id, username, email, password)
VALUES(2,'proghx', 'prog3@th-owl.de', 'sonne') ;
INSERT INTO users(id, username, email, password)
VALUES(3,'admin', 'adm_p3@th-owl.de', 'nimda') ;

-- skip used primary keys
ALTER SEQUENCE users_seq RESTART WITH 4 ;

VALUES ('Persönlich'); INSERT INTO category (name)
INSERT INTO category (name) VALUES ('Arbeit');
INSERT INTO category (name) VALUES ('Studium');
INSERT INTO category (name) VALUES ('Sonstiges');
