-- Create the "users" table
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,           -- Auto-incrementing ID
    username VARCHAR(255) NOT NULL,   -- Username of the user
    CONSTRAINT unique_username UNIQUE (username)  -- Ensure username is unique
);

-- Create the "notifications" table
CREATE TABLE IF NOT EXISTS notifications (
    id BIGSERIAL PRIMARY KEY,        -- Auto-incrementing ID
    username VARCHAR(255) NOT NULL,        -- Foreign key to the users table
    title VARCHAR(255) NOT NULL,     -- Title of the notification
    message TEXT NOT NULL,           -- Message content of the notification
    status VARCHAR(20) NOT NULL,     -- Status of the notification
    CONSTRAINT fk_user_username
        FOREIGN KEY (username)
        REFERENCES users (username)
        ON DELETE CASCADE            -- Deletes notifications if the user is deleted
);