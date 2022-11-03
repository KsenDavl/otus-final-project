CREATE TABLE IF NOT EXISTS meeting.meetings (

    id BIGSERIAL PRIMARY KEY,
    title  VARCHAR(255),
    description TEXT,
    date DATE,
    time TIME,
    approved BOOLEAN,
    capacity INTEGER,
    organizer_login VARCHAR(255),
    seats_left INTEGER
);

