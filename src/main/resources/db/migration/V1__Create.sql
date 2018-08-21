CREATE TABLE person (
  id            BIGSERIAL PRIMARY KEY,
  name          TEXT,
  age           INTEGER,
  created_at    TIMESTAMP,
  updated_at    TIMESTAMP
);