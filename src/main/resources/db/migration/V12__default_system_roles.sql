ALTER TABLE system_roles ADD COLUMN is_default BOOLEAN DEFAULT false;

UPDATE system_roles SET  is_default = true WHERE system_role_id = 1;
UPDATE system_roles SET  is_default = true WHERE system_role_id = 2;
UPDATE system_roles SET  is_default = true WHERE system_role_id = 3;
