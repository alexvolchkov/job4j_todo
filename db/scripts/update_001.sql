CREATE TABLE if not exists users (
  id SERIAL PRIMARY KEY,
  name TEXT unique,
  password TEXT
);

CREATE TABLE if not exists items (
  id SERIAL PRIMARY KEY,
  name TEXT,
  description VARCHAR,
  created TIMESTAMP,
  done BOOLEAN,
  user_id int not null references users(id)
);

