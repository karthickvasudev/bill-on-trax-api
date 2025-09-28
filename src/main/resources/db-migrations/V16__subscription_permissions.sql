-- Flyway migration: V16__subscription_permissions.sql
-- Register Subscriptions feature and permissions, and map them to permission groups (Super Admin, Owner)

insert into features (name, feature_code, is_custom_field_support)
values ('Subscriptions', 'SUBSCRIPTIONS', 0);

insert into permissions(name, permission_code) values
 ('Create Subscription Plan','CREATE_SUBSCRIPTION_PLAN'),
 ('Subscription Plans List View','LIST_SUBSCRIPTION_PLANS'),
 ('View Subscription Plan','VIEW_SUBSCRIPTION_PLAN'),
 ('Update Subscription Plan','UPDATE_SUBSCRIPTION_PLAN'),
 ('Delete Subscription Plan','DELETE_SUBSCRIPTION_PLAN'),
 ('Manage Plan Item Quotas','MANAGE_PLAN_ITEM_QUOTAS'),
 ('Create Customer Subscription','CREATE_CUSTOMER_SUBSCRIPTION'),
 ('Customer Subscriptions List View','LIST_CUSTOMER_SUBSCRIPTIONS'),
 ('View Customer Subscription','VIEW_CUSTOMER_SUBSCRIPTION'),
 ('Update Subscription Status','UPDATE_SUBSCRIPTION_STATUS'),
 ('Record Usage','RECORD_SUBSCRIPTION_USAGE'),
 ('View Usage Reports','VIEW_SUBSCRIPTION_USAGE');

-- Map permissions to Super Admin & Owner permission groups
insert into permission_feature_permission_group_map (permission_group_id, feature_id, permission_id)
select pg.id as permission_group_id, f.id as feature_id, p.id as permission_id
from permission_group pg, features f, permissions p
where f.feature_code = 'SUBSCRIPTIONS' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'CREATE_SUBSCRIPTION_PLAN'
union all
select pg.id, f.id, p.id from permission_group pg, features f, permissions p
where f.feature_code = 'SUBSCRIPTIONS' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'LIST_SUBSCRIPTION_PLANS'
union all
select pg.id, f.id, p.id from permission_group pg, features f, permissions p
where f.feature_code = 'SUBSCRIPTIONS' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'VIEW_SUBSCRIPTION_PLAN'
union all
select pg.id, f.id, p.id from permission_group pg, features f, permissions p
where f.feature_code = 'SUBSCRIPTIONS' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'UPDATE_SUBSCRIPTION_PLAN'
union all
select pg.id, f.id, p.id from permission_group pg, features f, permissions p
where f.feature_code = 'SUBSCRIPTIONS' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'DELETE_SUBSCRIPTION_PLAN'
union all
select pg.id, f.id, p.id from permission_group pg, features f, permissions p
where f.feature_code = 'SUBSCRIPTIONS' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'MANAGE_PLAN_ITEM_QUOTAS'
union all
select pg.id, f.id, p.id from permission_group pg, features f, permissions p
where f.feature_code = 'SUBSCRIPTIONS' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'CREATE_CUSTOMER_SUBSCRIPTION'
union all
select pg.id, f.id, p.id from permission_group pg, features f, permissions p
where f.feature_code = 'SUBSCRIPTIONS' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'LIST_CUSTOMER_SUBSCRIPTIONS'
union all
select pg.id, f.id, p.id from permission_group pg, features f, permissions p
where f.feature_code = 'SUBSCRIPTIONS' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'VIEW_CUSTOMER_SUBSCRIPTION'
union all
select pg.id, f.id, p.id from permission_group pg, features f, permissions p
where f.feature_code = 'SUBSCRIPTIONS' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'UPDATE_SUBSCRIPTION_STATUS'
union all
select pg.id, f.id, p.id from permission_group pg, features f, permissions p
where f.feature_code = 'SUBSCRIPTIONS' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'RECORD_SUBSCRIPTION_USAGE'
union all
select pg.id, f.id, p.id from permission_group pg, features f, permissions p
where f.feature_code = 'SUBSCRIPTIONS' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'VIEW_SUBSCRIPTION_USAGE';
