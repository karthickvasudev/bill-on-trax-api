-- Create warehouse table
create table warehouse (
  id bigint auto_increment,
  name varchar(255) not null,
  address text null,
  is_active boolean not null default true,
  created_time timestamp not null default current_timestamp,
  created_by bigint null,
  updated_time timestamp null,
  updated_by bigint null,
  primary key (id),
  unique key uk_warehouse_name (name)
);
create index idx_warehouse_name on warehouse(name);
create index idx_warehouse_active on warehouse(is_active);

-- Adjust foreign keys on inventory & inventory_history to point to warehouse table (if they previously referenced stores)
-- Safe guards: drop only if exists
alter table inventory drop foreign key fk_inventory_warehouse;
alter table inventory add constraint fk_inventory_warehouse foreign key (warehouse_id) references warehouse(id);

alter table inventory_history drop foreign key fk_inventory_history_warehouse;
alter table inventory_history add constraint fk_inventory_history_warehouse foreign key (warehouse_id) references warehouse(id);

-- Feature & permissions for Warehouse module
insert into features (name, feature_code, is_custom_field_support)
values ('Warehouse', 'WAREHOUSE', 0);

insert into permissions(name, permission_code) values
 ('Create Warehouse','CREATE_WAREHOUSE'),
 ('Warehouse List View','LIST_WAREHOUSE'),
 ('View Warehouse','VIEW_WAREHOUSE'),
 ('Update Warehouse','UPDATE_WAREHOUSE'),
 ('Delete Warehouse','DELETE_WAREHOUSE');

-- Map permissions to Super Admin & Owner permission groups
insert into permission_feature_permission_group_map (permission_group_id, feature_id, permission_id)
select pg.id as permission_group_id, f.id as feature_id, p.id as permission_id
from permission_group pg, features f, permissions p
where f.feature_code = 'WAREHOUSE' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'CREATE_WAREHOUSE'
union all
select pg.id, f.id, p.id from permission_group pg, features f, permissions p
where f.feature_code = 'WAREHOUSE' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'LIST_WAREHOUSE'
union all
select pg.id, f.id, p.id from permission_group pg, features f, permissions p
where f.feature_code = 'WAREHOUSE' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'VIEW_WAREHOUSE'
union all
select pg.id, f.id, p.id from permission_group pg, features f, permissions p
where f.feature_code = 'WAREHOUSE' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'UPDATE_WAREHOUSE'
union all
select pg.id, f.id, p.id from permission_group pg, features f, permissions p
where f.feature_code = 'WAREHOUSE' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'DELETE_WAREHOUSE';

