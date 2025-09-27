create table stores (
  id bigint auto_increment primary key,
  business_id bigint not null,
  name varchar(255) not null,
  address varchar(255) null,
  city varchar(255) null,
  state varchar(255) null,
  country varchar(255) null,
  zip_code varchar(255) null,
  logo_url varchar(255) null,
  is_deleted boolean not null default false,
  created_time datetime not null default current_timestamp,
  created_by bigint not null,
  updated_time datetime null,
  updated_by bigint null
);