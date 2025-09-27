-- Flyway migration: V12__orders_permissions.sql
-- Register Orders feature and permissions, and map them to permission groups (Super Admin, Owner)

insert into features (name, feature_code, is_custom_field_support)
values ('Orders', 'ORDERS', 0);

insert into permissions(name, permission_code) values
 ('Create Order','CREATE_ORDER'),
 ('Order List View','LIST_ORDERS'),
 ('View Order','VIEW_ORDER'),
 ('Update Order','UPDATE_ORDER'),
 ('Delete Order','DELETE_ORDER'),
 ('Update Order Status','UPDATE_ORDER_STATUS'),
 ('Process Order Payment','PROCESS_ORDER_PAYMENT');

-- Map permissions to Super Admin & Owner permission groups
insert into permission_feature_permission_group_map (permission_group_id, feature_id, permission_id)
select pg.id as permission_group_id, f.id as feature_id, p.id as permission_id
from permission_group pg, features f, permissions p
where f.feature_code = 'ORDERS' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'CREATE_ORDER'
union all
select pg.id, f.id, p.id from permission_group pg, features f, permissions p
where f.feature_code = 'ORDERS' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'LIST_ORDERS'
union all
select pg.id, f.id, p.id from permission_group pg, features f, permissions p
where f.feature_code = 'ORDERS' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'VIEW_ORDER'
union all
select pg.id, f.id, p.id from permission_group pg, features f, permissions p
where f.feature_code = 'ORDERS' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'UPDATE_ORDER'
union all
select pg.id, f.id, p.id from permission_group pg, features f, permissions p
where f.feature_code = 'ORDERS' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'DELETE_ORDER'
union all
select pg.id, f.id, p.id from permission_group pg, features f, permissions p
where f.feature_code = 'ORDERS' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'UPDATE_ORDER_STATUS'
union all
select pg.id, f.id, p.id from permission_group pg, features f, permissions p
where f.feature_code = 'ORDERS' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'PROCESS_ORDER_PAYMENT';

