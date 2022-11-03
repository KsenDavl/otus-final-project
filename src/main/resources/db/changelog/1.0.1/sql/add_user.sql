CREATE TABLE if not exists users
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL UNIQUE
);

-- CREATE TABLE if not exists meetings_users
-- (
--     meetings_id  BIGINT references meeting.meetings,
--     users_id BIGINT references meeting.users
-- )