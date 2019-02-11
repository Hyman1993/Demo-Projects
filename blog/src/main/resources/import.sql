INSERT INTO user (id, username, password, name, email) VALUES (1, 'admin', '$2a$10$sFsQUcypIhnx/tNcdf9okuMrkFj0iVAGEvi5aIYXXs8Yiuy.W.3/K', '管理员', '1014173114@qq.com');
INSERT INTO user (id, username, password, name, email)  VALUES (2, 'hyman', '$2a$10$TybjkmEBydAz9e62H8hYX.gZ9fvvKx4zRavSK8EbDaZe8VxORK1hO', '彭 煌', '1392841128@qq.com');
INSERT INTO user (id, username, password, name, email)  VALUES (3, 'visitor', '$2a$10$SiWVpZZgv7E623pjaUxQKuKxoREgttb.RkvHv6RRrke9PbBlEKbaC', '游 客', 'visitor@gmail.com');


INSERT INTO authority (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO authority (id, name) VALUES (2, 'ROLE_USER');


INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2);
INSERT INTO user_authority (user_id, authority_id) VALUES (3, 2);
INSERT INTO invitation_code (id, code,status) VALUES (1, 'aaaa',1);
INSERT INTO invitation_code (id, code,status) VALUES (2, 'bbbb',1);
INSERT INTO invitation_code (id, code,status) VALUES (3, 'cccc',1);