
-- custom_field_definition table
CREATE TABLE custom_field_definition (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  module enum('Customer', 'Product') NOT NULL,
  business_id BIGINT NOT NULL,
  field_name VARCHAR(100) NOT NULL,
  field_type enum('TEXT', 'NUMBER', 'DATE', 'DROPDOWN', 'CHOICE', 'MULTICHOICE', 'FILE') NOT NULL,
  is_required BOOLEAN NOT NULL,
  default_value TEXT,
  options JSON,
  created_time TIMESTAMP,
  updated_time TIMESTAMP,
  created_by BIGINT,
  updated_by BIGINT
);
-- custom_field_value table
CREATE TABLE custom_field_value (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  custom_field_id BIGINT NOT NULL,
  record_id BIGINT NOT NULL,
  value TEXT,
  FOREIGN KEY (custom_field_id) REFERENCES custom_field_definition (id)
);
CREATE INDEX idx_custom_field_definition_module_store ON custom_field_definition (module, business_id);
-- Index to quickly find all values for a record (e.g., all custom fields for a customer/product/order)
CREATE INDEX idx_custom_field_value_record ON custom_field_value (record_id);
-- Index to quickly find all values for a custom field (useful for reporting/analytics)
CREATE INDEX idx_custom_field_value_custom_field ON custom_field_value (custom_field_id);
-- Composite index for frequent queries filtering by custom_field_id and record_id together
CREATE INDEX idx_custom_field_value_field_record ON custom_field_value (custom_field_id, record_id);
alter table features
add column is_custom_field_support boolean default false;
update features
set is_custom_field_support = 1
where feature_code = 'CUSTOMER';

alter table custom_field_definition add column additional_options JSON;

alter table custom_field_definition add column is_deleted boolean default false;

alter table custom_field_definition
    modify field_type enum ('TEXT', 'NUMBER', 'DATE', 'DATETIME', 'DROPDOWN', 'CHOICE', 'MULTICHOICE', 'FILE') not null;

alter table custom_field_definition drop column default_value;

