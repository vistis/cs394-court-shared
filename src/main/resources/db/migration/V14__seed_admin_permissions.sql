INSERT INTO system_permissions (system_permission_id, code) VALUES
(6, 'ADMIN_PORTAL_ACCESS'),
(7, 'USER_VIEW'),
(8, 'USER_CREATE'),
(9, 'USER_UPDATE'),
(10, 'USER_DELETE'),
(11, 'ROLE_VIEW'),
(12, 'ROLE_CREATE'),
(13, 'ROLE_UPDATE'),
(14, 'ROLE_DELETE'),
(15, 'PERMISSION_VIEW'),
(16, 'STORAGE_VIEW');

INSERT INTO role_permissions (system_role_id, system_permission_id) VALUES
(1,6), (1, 7), (1, 11),
(3, 6), (3, 7), (3, 8), (3, 9), (3, 10), (3, 11), (3, 12), (3, 13), (3, 14), (3, 15), (3, 16);