CREATE TABLE system_roles (
    system_role_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE INDEX idx_name ON system_roles(name);

CREATE TABLE system_permissions (
    system_permission_id SERIAL PRIMARY KEY,
    code VARCHAR(255) NOT NULL
);

CREATE TABLE role_permissions (
    system_role_id INT NOT NULL,
    system_permission_id INT NOT NULL,
    PRIMARY KEY (system_role_id, system_permission_id),
    CONSTRAINT fk_role_permissions_role FOREIGN KEY (system_role_id) REFERENCES system_roles(system_role_id) ON DELETE CASCADE,
    CONSTRAINT fk_role_permissions_permission FOREIGN KEY (system_permission_id) REFERENCES system_permissions(system_permission_id) ON DELETE CASCADE
);

CREATE TABLE users (
    user_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    profile_picture_path VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,
    CONSTRAINT uq_users_username UNIQUE (username),
    CONSTRAINT uq_users_email UNIQUE (email)
);

CREATE UNIQUE INDEX idx_users_username ON users(username);

CREATE TABLE user_roles (
    user_id UUID NOT NULL,
    system_role_id INT NOT NULL,
    PRIMARY KEY (user_id, system_role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_role FOREIGN KEY (system_role_id) REFERENCES system_roles(system_role_id) ON DELETE CASCADE
);

INSERT INTO system_roles (system_role_id, name) VALUES
(1, 'CHIEF_GREFFIER'),
(2, 'GREFFIER'),
(3, 'ADMINISTRATOR');

INSERT INTO system_permissions (system_permission_id, code) VALUES
(1, 'CASE_VIEW'),
(2, 'CASE_CREATE'),
(3, 'CASE_UPDATE'),
(4, 'CASE_ASSIGN'),
(5, 'USER_MANAGE');

INSERT INTO role_permissions (system_role_id, system_permission_id) VALUES
(1, 1), (1, 3), (1, 4),
(2, 1), (2, 3),
(3, 5);

-- admin123
-- chief123
-- greffier123
INSERT INTO users (user_id, username, email, first_name, last_name, password, profile_picture_path, is_active, created_at, updated_at) VALUES
('2657220f-c267-48bf-835d-3fb97dd9ad68', 'admin.court', 'admin@court.gov.kh', 'Vichea', 'Sam', '$2b$12$eKR10mpra8BUKBwtM3PqDOELvqdf.y/UjMQ5NbS09.FiM2ptoM5Ui', 'profiles/users/default.jpg', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('53855a52-f1df-4b33-833f-c1de7a676d9d', 'chief.greffier', 'chief.greffier@court.gov.kh', 'Chanthou', 'Sok', '$2b$12$pnsbB2CjqI3kX.9kMgFMyuN.uYyFXSwF5Z4tKt5v6Jx1vO7JRUZWy', 'profiles/users/default.jpg', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('55fb903c-9b52-588e-cc02-df84239023bb', 'greffier.one', 'greffier1@court.gov.kh', 'Norith', 'Chan', '$2b$12$WaoIHRMHEioDgrgzUai8Wu8YsUuJVC8bzxfz9VlJFe2ioPfWW7Ca.', 'profiles/users/default.jpg', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO user_roles (user_id, system_role_id) VALUES
('2657220f-c267-48bf-835d-3fb97dd9ad68', 3),
('53855a52-f1df-4b33-833f-c1de7a676d9d', 1),
('55fb903c-9b52-588e-cc02-df84239023bb', 2);
