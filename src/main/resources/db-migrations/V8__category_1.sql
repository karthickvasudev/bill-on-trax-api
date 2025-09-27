alter table category
add column created_time timestamp not null default current_timestamp,
add column created_by bigint null,
add column updated_time timestamp null,
add column updated_by bigint null;
