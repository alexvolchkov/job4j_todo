CREATE TABLE if not exists users (
  id SERIAL PRIMARY KEY,
  name VARCHAR unique,
  password VARCHAR
);

CREATE TABLE if not exists categories (
  id SERIAL PRIMARY KEY,
  name VARCHAR
);

CREATE TABLE if not exists items (
  id SERIAL PRIMARY KEY,
  name VARCHAR,
  description VARCHAR,
  created TIMESTAMP,
  done BOOLEAN,
  user_id int not null references users(id)
);
--
--ALTER TABLE items add column if not exists

