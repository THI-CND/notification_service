-- Create the "notifications" table
CREATE TABLE IF NOT EXISTS notifications (
    id BIGINT NOT NULL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    status VARCHAR(10) NOT NULL CHECK (status IN ('READ', 'UNREAD', 'DELETED'))
);