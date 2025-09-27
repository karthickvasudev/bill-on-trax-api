create table roles (
  id bigint auto_increment,
  name varchar(255) not null,
  business_id bigint not null,
  permission_group_id bigint not null,
  created_time datetime not null default current_timestamp,
  updated_time datetime null,
  primary key (id)
);
create table role_user_map (
  id bigint auto_increment,
  user_id bigint not null,
  role_id bigint not null,
  primary key (id)
);
create table permission_group (
  id bigint auto_increment,
  name varchar(255) not null,
  is_default boolean not null default false,
  is_deleted boolean not null default false,
  created_time datetime not null default current_timestamp,
  updated_time datetime null,
  primary key (id)
);
create table features (
  id bigint auto_increment,
  name varchar(255) not null,
  feature_code varchar(255) not null,
  primary key (id)
);
create table permissions (
  id bigint auto_increment,
  name varchar(255) not null,
  permission_code varchar(255) not null,
  primary key (id)
);
create table feature_permission_map (
  id bigint auto_increment,
  feature_id bigint not null,
  permission_id bigint not null,
  primary key (id)
);
create table permission_feature_permission_group_map (
  id bigint auto_increment,
  permission_group_id bigint not null,
  feature_id bigint not null,
  permission_id bigint not null,
  primary key (id)
);
insert into roles (
    name,
    business_id,
    permission_group_id,
    created_time,
    updated_time
  )
values ('Super Admin', 0, 1, current_timestamp, null);
insert into role_user_map (user_id, role_id)
values (1, 1);
insert into permission_group (
    name,
    is_default,
    is_deleted,
    created_time,
    updated_time
  )
values (
    'Super Admin Permissions',
    true,
    false,
    current_timestamp,
    null
  ),
  (
    'Owner permissions',
    true,
    false,
    current_timestamp,
    null
  );
insert into features (name, feature_code)
values ('Business', 'BUSINESS');
insert into permissions(name, permission_code)
values (
    'Business List and Detailed View',
    'VIEW_BUSINESS'
  ),
  ('Create Business', 'CREATE_BUSINESS'),
  ('Update Business', 'UPDATE_BUSINESS'),
  ('Delete Business', 'DELETE_BUSINESS');
insert into permission_feature_permission_group_map (permission_group_id, feature_id, permission_id)
values (1, 1, 1),
  (1, 1, 2),
  (1, 1, 3),
  (1, 1, 4);
create table business (
  id bigint auto_increment primary key,
  owner_id bigint null,
  name varchar(255) not null,
  email varchar(255) not null unique,
  phone_number varchar(255) not null unique,
  address varchar(255) null,
  city varchar(255) null,
  state varchar(255) null,
  country varchar(255) null,
  zip_code varchar(255) null,
  logo_url varchar(255) null,
  invite_id varchar(255) null,
  status enum(
    'CREATED',
    'INVITED',
    'BUSINESS_DETAILS_UPDATED',
    'OWNER_DETAILS_UPDATED',
    'ACTIVE',
    'INACTIVE',
    'DELETED'
  ) not null default 'CREATED',
  created_time datetime not null default current_timestamp,
  updated_time datetime null
);