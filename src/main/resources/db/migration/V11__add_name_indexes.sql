CREATE INDEX idx_cases_title ON cases(title);
CREATE INDEX idx_participant_name ON participants(name);
CREATE INDEX idx_lawyers_name ON lawyers(first_name, last_name);
CREATE INDEX idx_judges_name ON judges(first_name, last_name);
CREATE INDEX idx_users_name ON users(first_name, last_name);
