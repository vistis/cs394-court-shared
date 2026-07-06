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
(3, 1), (3, 2), (3, 3), (3, 4), (3, 5);

-- admin123
-- chief123
-- greffier123
INSERT INTO users (user_id, username, email, first_name, last_name, password, profile_picture_path, is_active, created_at, updated_at) VALUES
('9a7b055e-1140-4f51-872c-ec7e4dfce905', 'admin.court', 'admin@court.gov.kh', 'Vichea', 'Sam', '$2a$12$7b9e022c82515a62983dfd8f5egdf002ebf903c9b52588ecc02df', 'profiles/users/default.jpg', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('0f1a892b-8a41-477d-bb91-ce73128912aa', 'chief.greffier', 'chief.greffier@court.gov.kh', 'Chanthou', 'Sok', '$2a$12$11fa892b8a41477dbb91ce73128912ef22fb903c9b52588ecc02df', 'profiles/users/default.jpg', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('55fb903c-9b52-588e-cc02-df84239023bb', 'greffier.one', 'greffier1@court.gov.kh', 'Norith', 'Chan', '$2a$12$22fb903c9b52588ecc02df84239023f01fa892b8a41477dbb91ce', 'profiles/users/default.jpg', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO user_roles (user_id, system_role_id) VALUES
('9a7b055e-1140-4f51-872c-ec7e4dfce905', 3),
('0f1a892b-8a41-477d-bb91-ce73128912aa', 1),
('55fb903c-9b52-588e-cc02-df84239023bb', 2);
