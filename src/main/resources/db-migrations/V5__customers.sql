-- customer table
CREATE TABLE customers (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  business_id BIGINT NOT NULL,
  customer_code VARCHAR(64) NULL UNIQUE,
  type VARCHAR(16) NOT NULL,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255),
  phone VARCHAR(32),
  alternate_phone VARCHAR(32),
  billing_address TEXT,
  shipping_address TEXT,
  tax_id VARCHAR(64),
  outstanding_limit DECIMAL(19, 2),
  note TEXT,
  created_by BIGINT NOT NULL,
  created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_by BIGINT NULL,
  updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE customer_contacts (
  id BIGINT primary key AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100),
  phone VARCHAR(20),
  customer_id BIGINT NOT NULL,
  FOREIGN KEY (customer_id) REFERENCES customers (id) ON DELETE CASCADE
);

ALTER TABLE customer_contacts
ADD CONSTRAINT fk_contact_person_customer FOREIGN KEY (customer_id) REFERENCES customers (id);

CREATE UNIQUE INDEX idx_customers_business_phone ON customers (business_id, phone);

CREATE INDEX idx_customer_contact ON customer_contacts (customer_id);

insert into features (name, feature_code)
values ('Customer', 'CUSTOMER');

insert into permissions(name, permission_code)
values ('Customer List and Detailed View','VIEW_CUSTOMER'),
  ('Create Customer', 'CREATE_CUSTOMER'),
  ('Update Customer', 'UPDATE_CUSTOMER'),
  ('Delete Customer', 'DELETE_CUSTOMER');

insert into permission_feature_permission_group_map (permission_group_id, feature_id, permission_id)
values (2, 2, 5),
  (2, 2, 6),
  (2, 2, 7),
  (2, 2, 8);

insert into features (name, feature_code)
values ('Custom Fields', 'CUSTOM_FIELDS');

insert into permission_feature_permission_group_map (permission_group_id, feature_id, permission_id)
values (3, 3, 9),
  (3, 3, 10),
  (3, 3, 11),
  (3, 3, 12);