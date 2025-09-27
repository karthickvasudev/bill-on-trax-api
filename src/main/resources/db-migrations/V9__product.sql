create table product (
  id bigint auto_increment,
  name varchar(255) not null,
  sku varchar(255) not null,
  type enum('PHYSICAL','SERVICE','DIGITAL') not null,
  price decimal(15,2) not null,
  tax_inclusive boolean not null,
  is_active boolean not null default true,
  description text null,
  barcode varchar(255) null,
  unit varchar(100) null,
  category_id bigint null,
  brand varchar(255) null,
  hsn_code varchar(100) null,
  custom_fields text null,
  stock_quantity int null,
  cost_price decimal(15,2) null,
  low_stock_alert int null,
  warehouse_id bigint null,
  reorder_level int null,
  tax_rate decimal(5,2) not null,
  created_time timestamp not null default current_timestamp,
  created_by bigint null,
  updated_time timestamp null,
  updated_by bigint null,
  primary key (id),
  unique key uk_product_sku (sku)
);

create table product_tags (
  id bigint auto_increment,
  product_id bigint not null,
  tag varchar(100) not null,
  primary key (id),
  constraint fk_product_tags_product foreign key (product_id) references product(id)
);
create index idx_product_tags_product on product_tags(product_id);
create index idx_product_tags_tag on product_tags(tag);

-- Feature & permissions for Product module
insert into features (name, feature_code, is_custom_field_support)
values ('Product', 'PRODUCT', 1);

insert into permissions(name, permission_code) values
 ('Create Product','CREATE_PRODUCT'),
 ('Product List View','LIST_PRODUCT'),
 ('View Product','VIEW_PRODUCT'),
 ('Update Product','UPDATE_PRODUCT'),
 ('Delete Product','DELETE_PRODUCT');

-- Map permissions to Super Admin & Owner permission groups
insert into permission_feature_permission_group_map (permission_group_id, feature_id, permission_id)
select pg.id as permission_group_id, f.id as feature_id, p.id as permission_id
from permission_group pg, features f, permissions p
where f.feature_code = 'PRODUCT' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'CREATE_PRODUCT'
union all
select pg.id, f.id, p.id from permission_group pg, features f, permissions p
where f.feature_code = 'PRODUCT' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'LIST_PRODUCT'
union all
select pg.id, f.id, p.id from permission_group pg, features f, permissions p
where f.feature_code = 'PRODUCT' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'VIEW_PRODUCT'
union all
select pg.id, f.id, p.id from permission_group pg, features f, permissions p
where f.feature_code = 'PRODUCT' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'UPDATE_PRODUCT'
union all
select pg.id, f.id, p.id from permission_group pg, features f, permissions p
where f.feature_code = 'PRODUCT' and pg.name in ('Super Admin Permissions','Owner permissions') and p.permission_code = 'DELETE_PRODUCT';

