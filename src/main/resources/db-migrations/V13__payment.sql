-- payment table
CREATE TABLE payment (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  payment_number VARCHAR(100) UNIQUE,
  customer_id BIGINT NOT NULL,
  order_id BIGINT NULL,
  amount_paid DECIMAL(19,2) NOT NULL,
  discount_given DECIMAL(19,2) DEFAULT 0.00,
  payment_mode VARCHAR(50) NOT NULL,
  reference_number VARCHAR(255),
  payment_date TIMESTAMP NOT NULL,
  notes TEXT,
  created_by BIGINT NOT NULL,
  created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_by BIGINT NULL,
  updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
  CONSTRAINT fk_payment_customer FOREIGN KEY (customer_id) REFERENCES customers(id),
  CONSTRAINT fk_payment_order FOREIGN KEY (order_id) REFERENCES orders(id)
);

CREATE UNIQUE INDEX idx_payment_number ON payment (payment_number);
CREATE INDEX idx_payment_customer ON payment (customer_id);
CREATE INDEX idx_payment_order ON payment (order_id);

