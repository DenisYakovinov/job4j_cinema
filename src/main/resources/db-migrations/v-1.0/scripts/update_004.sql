INSERT INTO users (name, email, phone, password) VALUES ('ImaginaryUser', 'user@mail.com', '+1 111 11 11', '123');

INSERT INTO sessions (name) VALUES ('imaginary movie (morning)');
INSERT INTO sessions (name) VALUES ('imaginary movie (afternoon)');
INSERT INTO sessions (name) VALUES ('imaginary movie (evening)');
INSERT INTO sessions (name) VALUES ('imaginary movie (night)');
INSERT INTO sessions (name) VALUES ('another movie (morning)');
INSERT INTO sessions (name) VALUES ('another movie (afternoon)');
INSERT INTO sessions (name) VALUES ('another movie (evening)');
INSERT INTO sessions (name) VALUES ('another movie (night)');

INSERT INTO ticket (session_id, row, cell, user_id) VALUES (1, 1, 2, 1);
INSERT INTO ticket (session_id, row, cell, user_id) VALUES (1, 2, 1, 1);
INSERT INTO ticket (session_id, row, cell, user_id) VALUES (1, 3, 4, 1);
INSERT INTO ticket (session_id, row, cell, user_id) VALUES (1, 2, 3, 1);

INSERT INTO ticket (session_id, row, cell, user_id) VALUES (2, 2, 2, 1);
INSERT INTO ticket (session_id, row, cell, user_id) VALUES (2, 1, 3, 1);
INSERT INTO ticket (session_id, row, cell, user_id) VALUES (2, 4, 1, 1);
INSERT INTO ticket (session_id, row, cell, user_id) VALUES (2, 3, 3, 1);
