-- Flyway migration: create orders, order_items, order_payments

CREATE TABLE IF NOT EXISTS orders (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  order_number VARCHAR(100) NOT NULL UNIQUE,
  customer_id BIGINT NOT NULL,
  order_type VARCHAR(50) NOT NULL,
  status VARCHAR(50) NOT NULL,
  total_amount DECIMAL(19,2) NOT NULL DEFAULT 0.00,
  discount_amount DECIMAL(19,2) DEFAULT 0.00,
  tax_amount DECIMAL(19,2) DEFAULT 0.00,
  final_amount DECIMAL(19,2) NOT NULL DEFAULT 0.00,
  payment_status VARCHAR(50) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  created_by BIGINT,
  notes TEXT,
  is_deleted BOOLEAN DEFAULT FALSE,
  CONSTRAINT fk_orders_customer FOREIGN KEY (customer_id) REFERENCES customers(id)
);

CREATE TABLE IF NOT EXISTS order_items (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  order_id BIGINT NOT NULL,
  item_type VARCHAR(50) NOT NULL,
  item_id BIGINT NOT NULL,
  description VARCHAR(255),
  quantity INT NOT NULL,
  unit_price DECIMAL(19,2) NOT NULL,
  total_price DECIMAL(19,2) NOT NULL,
  tax_percentage DECIMAL(5,2),
  final_price DECIMAL(19,2) NOT NULL,
  CONSTRAINT fk_orderitems_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS order_payments (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  order_id BIGINT NOT NULL,
  amount DECIMAL(19,2) NOT NULL,
  payment_mode VARCHAR(50) NOT NULL,
  reference_no VARCHAR(255),
  paid_on TIMESTAMP NOT NULL,
  notes TEXT,
  CONSTRAINT fk_orderpayments_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

