-- This file allows us to load static data into the test database before tests are run.

-- Passwords are in the format: Password<UserLetter>123. Unless specified otherwise.
INSERT INTO users (email, first_name, last_name, password, username, email_verified)
VALUES ('UserA@test.com', 'UserA-FirstName', 'UserA-LastName', '$2a$10$hBn5gu6cGelJNiE6DDsaBOmZgyumCSzVwrOK/37FWgJ6aLIdZSSI2', 'UserA', true)
     , ('UserB@test.com', 'UserB-FirstName', 'UserB-LastName', '$2a$10$TlYbg57fqOy/1LJjispkjuSIvFJXbh3fy0J9fvHnCpuntZOITAjVG', 'UserB', false);