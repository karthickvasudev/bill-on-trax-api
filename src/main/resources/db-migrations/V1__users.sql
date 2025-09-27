create table authentication_details (
  id varchar(255) primary key,
  user_id bigint not null unique,
  business_id bigint not null,
  created_time datetime not null default current_timestamp
);
create table users (
  id bigint auto_increment,
  business_id bigint not null,
  name varchar(255) not null,
  username varchar(255) null,
  email varchar(255) not null,
  phone_number varchar(255) not null,
  password varchar(255) null,
  is_password_reset_required boolean not null default true,
  is_deleted boolean not null default false,
  created_time timestamp not null default current_timestamp,
  updated_time timestamp null,
  primary key (id)
);
insert into users (
    business_id,
    name,
    username,
    email,
    phone_number,
    password,
    is_password_reset_required,
    is_deleted
  )
values (
    0,
    'Super Admin',
    'karthickvasudevan143',
    'karthick.vasudevan.143@gmail.com',
    '9361312424',
    'bill_on_trax_first_time_login',
    true,
    false
  );