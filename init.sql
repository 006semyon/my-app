CREATE TABLE role
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    role VARCHAR(255) NOT NULL
);

INSERT INTO role (role) VALUES ('ROLE_ADMIN');
INSERT INTO role (role) VALUES ('ROLE_USER');