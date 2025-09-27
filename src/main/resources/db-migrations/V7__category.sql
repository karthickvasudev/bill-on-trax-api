create table category (
  id bigint AUTO_INCREMENT,
  parent_category_id bigint null,
  business_id bigint not null,
  name varchar(255) not null,
  hsn_code varchar(255) null,
  gst_rate decimal(5,2) null,
  is_deleted boolean not null default false,
  primary key (id)
);

insert into features (name, feature_code, is_custom_field_support)
values ('Category', 'CATEGORY', 0);

insert into permissions(name, permission_code)
values ('Create Category','CREATE_CATEGORY'),
  ('Category List View', 'LIST_CATEGORY');

insert into permission_feature_permission_group_map (permission_group_id, feature_id, permission_id)
values (
    (select id from permission_group where name = 'Super Admin Permissions'),
    (select id from features where feature_code = 'CATEGORY'),
    (select id from permissions where permission_code = 'CREATE_CATEGORY')
),
(
    (select id from permission_group where name = 'Super Admin Permissions'),
    (select id from features where feature_code = 'CATEGORY'),
    (select id from permissions where permission_code = 'LIST_CATEGORY')
),
(
    (select id from permission_group where name = 'Owner permissions'),
    (select id from features where feature_code = 'CATEGORY'),
    (select id from permissions where permission_code = 'CREATE_CATEGORY')
),
(
    (select id from permission_group where name = 'Owner permissions'),
    (select id from features where feature_code = 'CATEGORY'),
    (select id from permissions where permission_code = 'LIST_CATEGORY')
);