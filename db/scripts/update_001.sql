CREATE TABLE if not exists items (
  id SERIAL PRIMARY KEY,
  name VARCHAR,
  description VARCHAR,
  created TIMESTAMP,
  done BOOLEAN
);