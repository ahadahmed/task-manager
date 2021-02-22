
-- USER
-- hashed password: letmein
INSERT IGNORE INTO security_user (id, username, password, first_name, last_name) VALUES
(1,  'admin', '$2a$12$ZhGS.zcWt1gnZ9xRNp7inOvo5hIT0ngN7N.pN939cShxKvaQYHnnu', 'Administrator', 'Adminstrator'),
(2,  'user1', '$2a$12$ZhGS.zcWt1gnZ9xRNp7inOvo5hIT0ngN7N.pN939cShxKvaQYHnnu', 'cardinity-user1', 'Doe');

-- ROLES

INSERT IGNORE INTO security_role (id, role_name, description) VALUES (1, 'ROLE_ADMIN', 'Administrator');
INSERT IGNORE INTO security_role (id, role_name, description) VALUES (2, 'ROLE_USER', 'Normal User');


delete from user_role; -- delete all existing 'user <-> role' mapping.

INSERT IGNORE INTO user_role(user_id, role_id) VALUES
 (1, 1), -- give admin ROLE_ADMIN
 (2, 2);  -- give user1 ROLE_USER
