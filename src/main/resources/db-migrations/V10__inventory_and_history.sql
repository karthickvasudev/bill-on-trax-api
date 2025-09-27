-- filepath: src/main/resources/db-migrations/V10__inventory_and_history.sql

-- Create inventory table
create table inventory (
  id bigint auto_increment,
  inventory_id varchar(255) not null,
  product_id bigint not null,
  warehouse_id bigint null,
  stock_quantity int not null,
  low_stock_alert int null,
  reorder_level int null,
  deleted boolean not null default false,
  created_time timestamp not null default current_timestamp,
  created_by bigint null,
  updated_time timestamp null,
  updated_by bigint null,
  primary key (id),
  unique key uk_inventory_inventory_id (inventory_id),
  constraint fk_inventory_product foreign key (product_id) references product(id),
  constraint fk_inventory_warehouse foreign key (warehouse_id) references stores(id)
);
create index idx_inventory_product on inventory(product_id);
create index idx_inventory_warehouse on inventory(warehouse_id);

-- Create inventory_history table
create table inventory_history (
  id bigint auto_increment,
  product_id bigint not null,
  warehouse_id bigint null,
  type enum('STOCK_IN','STOCK_OUT','ADJUSTMENT') not null,
  quantity int not null,
  reason text null,
  reference_id varchar(255) null,
  created_time timestamp not null default current_timestamp,
  created_by bigint not null,
  primary key (id),
  constraint fk_inventory_history_product foreign key (product_id) references product(id),
  constraint fk_inventory_history_warehouse foreign key (warehouse_id) references stores(id)
);
create index idx_inventory_history_product on inventory_history(product_id);
create index idx_inventory_history_warehouse on inventory_history(warehouse_id);
create index idx_inventory_history_created_time on inventory_history(created_time);

-- Feature & permissions for Inventory module
insert into features (name, feature_code, is_custom_field_support)
values ('Inventory', 'INVENTORY', 0);

insert into permissions(name, permission_code) values
 ('Create Inventory','CREATE_INVENTORY'),
 ('Inventory List View','LIST_INVENTORY'),
 ('View Inventory','VIEW_INVENTORY'),
 ('Update Inventory','UPDATE_INVENTORY'),
 ('Delete Inventory','DELETE_INVENTORY');

-- Map permissions to Super Admin & Owner permission groups
insert into permission_feature_permission_group_map (permission_group_id, feature_id, permission_id)
select pg.id as permission_group_id, f.id as feature_id, p.id as permission_id
from permission_group pg, features f, permissions p
where f.feature_code = 'INVENTORY' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'CREATE_INVENTORY'
union all
select pg.id, f.id, p.id from permission_group pg, features f, permissions p
where f.feature_code = 'INVENTORY' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'LIST_INVENTORY'
union all
select pg.id, f.id, p.id from permission_group pg, features f, permissions p
where f.feature_code = 'INVENTORY' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'VIEW_INVENTORY'
union all
select pg.id, f.id, p.id from permission_group pg, features f, permissions p
where f.feature_code = 'INVENTORY' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'UPDATE_INVENTORY'
union all
select pg.id, f.id, p.id from permission_group pg, features f, permissions p
where f.feature_code = 'INVENTORY' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'DELETE_INVENTORY';
