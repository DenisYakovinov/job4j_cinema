CREATE TABLE ticket
(
    id         SERIAL PRIMARY KEY,
    session_id INT NOT NULL REFERENCES sessions (id),
    "row"        INT NOT NULL  CHECK ("row" > 0 and "row" < 5),
    cell       INT NOT NULL CHECK (cell > 0 and cell < 5),
    user_id    INT NOT NULL REFERENCES users (id)
);
ALTER TABLE ticket ADD CONSTRAINT ticket_unique UNIQUE (session_id, "row" , cell);