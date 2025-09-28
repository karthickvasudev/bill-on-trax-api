-- Payment feature & permissions

-- Insert Payment feature (id auto-generated)
INSERT INTO features (name, feature_code)
VALUES ('Payment', 'PAYMENT');

-- Insert Payment permissions
INSERT INTO permissions (name, permission_code)
VALUES ('Payment List and Detailed View', 'VIEW_PAYMENT'),
       ('Create Payment', 'CREATE_PAYMENT'),
       ('Update Payment', 'UPDATE_PAYMENT'),
       ('Delete Payment', 'DELETE_PAYMENT');

-- Map permissions to the feature and an existing permission group (assuming group 2 like other core entities)
INSERT INTO permission_feature_permission_group_map (permission_group_id, feature_id, permission_id)
SELECT 2, f.id, p.id FROM features f, permissions p WHERE f.feature_code='PAYMENT' AND p.permission_code='VIEW_PAYMENT';
INSERT INTO permission_feature_permission_group_map (permission_group_id, feature_id, permission_id)
SELECT 2, f.id, p.id FROM features f, permissions p WHERE f.feature_code='PAYMENT' AND p.permission_code='CREATE_PAYMENT';
INSERT INTO permission_feature_permission_group_map (permission_group_id, feature_id, permission_id)
SELECT 2, f.id, p.id FROM features f, permissions p WHERE f.feature_code='PAYMENT' AND p.permission_code='UPDATE_PAYMENT';
INSERT INTO permission_feature_permission_group_map (permission_group_id, feature_id, permission_id)
SELECT 2, f.id, p.id FROM features f, permissions p WHERE f.feature_code='PAYMENT' AND p.permission_code='DELETE_PAYMENT';

