create table authentication_details
(
    id           varchar(255) primary key,
    user_id      bigint   not null unique,
    business_id  bigint   not null,
    created_time datetime not null default current_timestamp
);

create table users
(
    id                         bigint auto_increment,
    business_id                bigint       not null,
    name                       varchar(255) not null,
    username                   varchar(255) null,
    email                      varchar(255) not null,
    phone_number               varchar(255) not null,
    password                   varchar(255) null,
    is_password_reset_required boolean      not null default true,
    is_deleted                 boolean      not null default false,
    created_time               timestamp    not null default current_timestamp,
    updated_time               timestamp    null,
    primary key (id)
);

insert into users (business_id, name, username, email, phone_number, password, is_password_reset_required, is_deleted)
values (0, 'Super Admin', 'karthickvasudevan143', 'karthick.vasudevan.143@gmail.com', '9361312424',
        'bill_on_trax_first_time_login', true, false);

create table roles
(
    id                  bigint auto_increment,
    name                varchar(255) not null,
    business_id         bigint       not null,
    permission_group_id bigint       not null,
    created_time        datetime     not null default current_timestamp,
    updated_time        datetime     null,
    primary key (id)
);

create table role_user_map
(
    id      bigint auto_increment,
    user_id bigint not null,
    role_id bigint not null,
    primary key (id)
);

create table permission_group
(
    id           bigint auto_increment,
    name         varchar(255) not null,
    is_default   boolean      not null default false,
    is_deleted   boolean      not null default false,
    created_time datetime     not null default current_timestamp,
    updated_time datetime     null,
    primary key (id)
);

create table features
(
    id           bigint auto_increment,
    name         varchar(255) not null,
    feature_code varchar(255) not null,
    primary key (id)
);

create table permissions
(
    id              bigint auto_increment,
    name            varchar(255) not null,
    permission_code varchar(255) not null,
    primary key (id)
);

create table feature_permission_map
(
    id            bigint auto_increment,
    feature_id    bigint not null,
    permission_id bigint not null,
    primary key (id)
);

create table permission_feature_permission_group_map
(
    id                  bigint auto_increment,
    permission_group_id bigint not null,
    feature_id          bigint not null,
    permission_id       bigint not null,
    primary key (id)
);

insert into roles (name, business_id, permission_group_id, created_time, updated_time)
values ('Super Admin', 0, 1, current_timestamp, null);

insert into role_user_map (user_id, role_id)
values (1, 1);

insert into permission_group (name, is_default, is_deleted, created_time, updated_time)
values ('Super Admin Permissions', true, false, current_timestamp, null),
       ('Owner permissions', true, false, current_timestamp, null);


insert into features (name, feature_code)
values ('Business', 'BUSINESS');

insert into permissions(name, permission_code)
values ('Business List and Detailed View', 'VIEW_BUSINESS'),
       ('Create Business', 'CREATE_BUSINESS'),
       ('Update Business', 'UPDATE_BUSINESS'),
       ('Delete Business', 'DELETE_BUSINESS');

insert into permission_feature_permission_group_map (permission_group_id, feature_id, permission_id)
values (1, 1, 1),
       (1, 1, 2),
       (1, 1, 3),
       (1, 1, 4);


create table business
(
    id           bigint auto_increment primary key,
    owner_id     bigint                                                      null,
    name         varchar(255)                                                not null,
    email        varchar(255)                                                not null unique,
    phone_number varchar(255)                                                not null unique,
    address      varchar(255)                                                null,
    city         varchar(255)                                                null,
    state        varchar(255)                                                null,
    country      varchar(255)                                                null,
    zip_code     varchar(255)                                                null,
    logo_url     varchar(255)                                                null,
    invite_id    varchar(255)                                                null,
    status       enum ('CREATED','INVITED', 'ACTIVE', 'INACTIVE', 'DELETED') not null default 'CREATED',
    created_time datetime                                                    not null default current_timestamp,
    updated_time datetime                                                    null
);

create table template
(
    id             bigint auto_increment primary key,
    business_id    bigint                   not null,
    name           varchar(255)             not null,
    template       enum ('BUSINESS_INVITE') not null,
    html_content   text                     not null,
    is_super_admin boolean                  not null default false,
    is_deleted     boolean                  not null default false,
    created_time   datetime                 not null default current_timestamp,
    updated_time   datetime                 null
);

insert into template (business_id, name, template, html_content, is_super_admin)
values (0, 'Business Invite', 'BUSINESS_INVITE',
        '<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>Welcome to Bill on Trax</title>
    <style>
      body {
        background-color: #f4f7fa;
        margin: 0;
        padding: 20px;
        font-family: ''Segoe UI'', Tahoma, Geneva, Verdana, sans-serif;
        color: #333;
      }

      .container {
        max-width: 600px;
        margin: auto;
        background: #ffffff;
        border-radius: 10px;
        padding: 30px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
        text-align: center;
      }

      .logo {
        margin-bottom: 20px;
      }

      .logo img {
        height: 50px;
      }

      .title {
        font-size: 22px;
        font-weight: bold;
        color: #3b82f6;
        margin-bottom: 10px;
      }

      .message {
        font-size: 16px;
        margin-bottom: 30px;
      }

      .button {
        display: inline-block;
        background-color: #3b82f6;
        color: #ffffff;
        text-decoration: none;
        padding: 12px 24px;
        border-radius: 6px;
        font-weight: bold;
        font-size: 16px;
      }

      .footer {
        margin-top: 40px;
        font-size: 12px;
        color: #888;
      }
    </style>
  </head>
  <body>
    <div class="container">
      <div class="logo">
        <img src="https://yourdomain.com/logo.png" alt="Bill on Trax Logo" />
      </div>

      <div class="title">Welcome to Bill on Trax, {{userName}}!</div>

      <div class="message">
        Thank you for choosing <strong>Bill on Trax</strong> to power your business.<br />
        We’re here to help you manage billing, inventory, services, and more — all in one place.
        <br /><br />
        Click below to get started with setting up your account:
      </div>

      <a href="{{inviteLink}}" class="button">Create Account</a>

      <div class="footer">
        &copy; 2025 Bill on Trax. All rights reserved.<br />
        Need help? Contact us at <a href="mailto:support@billontrax.com">support@billontrax.com</a>
      </div>
    </div>
  </body>
</html>
', true);

create table stores
(
    id           bigint auto_increment primary key,
    business_id  bigint       not null,
    name         varchar(255) not null,
    email        varchar(255) not null unique,
    phone_number varchar(255) not null unique,
    address      varchar(255) null,
    city         varchar(255) null,
    state        varchar(255) null,
    country      varchar(255) null,
    zip_code     varchar(255) null,
    logo_url     varchar(255) null,
    is_deleted   boolean      not null default false,
    created_by   bigint       not null,
    created_time datetime     not null default current_timestamp,
    updated_time datetime     null
)