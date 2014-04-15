DROP table users if exists;
DROP table user_roles if exists;
CREATE TABLE users (name VARCHAR(50),pass VARCHAR(50));
CREATE TABLE user_roles (user_name VARCHAR(50), role_name VARCHAR(50));
INSERT INTO users(name, pass) VALUES ('admin', 'admin');
INSERT INTO users(name, pass) VALUES ('user', 'user');
INSERT INTO user_roles(user_name, role_name) VALUES ('admin', 'ADMIN');
INSERT INTO user_roles(user_name, role_name) VALUES ('user', 'USER');
