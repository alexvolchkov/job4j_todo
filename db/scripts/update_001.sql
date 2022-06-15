CREATE TABLE if not exists items (
  id SERIAL PRIMARY KEY,
  name VARCHAR,
  description VARCHAR,
  created TIMESTAMP,
  done BOOLEAN,
  user_id int not null references users(id)
);

CREATE TABLE if not exists users (
  id SERIAL PRIMARY KEY,
  name TEXT unique,
  password TEXT
);