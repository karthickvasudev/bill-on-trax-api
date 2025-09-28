-- Subscription Management Tables Migration
-- Create subscription_plan table
CREATE TABLE subscription_plan (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    price DECIMAL(17,2) NOT NULL,
    plan_type ENUM('PER_ITEM_QUOTA', 'CREDIT') NOT NULL,
    validity_days INT NOT NULL,
    description TEXT,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    INDEX idx_plan_name (name),
    INDEX idx_plan_type (plan_type)
);

-- Create subscription_plan_item_quota table
CREATE TABLE subscription_plan_item_quota (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    plan_id BIGINT NOT NULL,
    item_type ENUM('PRODUCT', 'SERVICE') NOT NULL,
    item_id BIGINT NOT NULL,
    allowed_quantity INT NOT NULL DEFAULT 0,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    CONSTRAINT fk_quota_plan FOREIGN KEY (plan_id) REFERENCES subscription_plan(id) ON DELETE CASCADE,
    UNIQUE KEY uk_plan_item (plan_id, item_type, item_id),
    INDEX idx_quota_plan (plan_id),
    INDEX idx_quota_item (item_type, item_id)
);

-- Create customer_subscription table
CREATE TABLE customer_subscription (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    plan_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status ENUM('ACTIVE', 'CANCELLED', 'EXPIRED') NOT NULL DEFAULT 'ACTIVE',
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    CONSTRAINT fk_customer_subscription_plan FOREIGN KEY (plan_id) REFERENCES subscription_plan(id),
    INDEX idx_customer_subscription (customer_id),
    INDEX idx_subscription_status (status),
    INDEX idx_subscription_dates (start_date, end_date),
    INDEX idx_customer_status (customer_id, status)
);

-- Create subscription_item_usage table
CREATE TABLE subscription_item_usage (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_subscription_id BIGINT NOT NULL,
    item_type ENUM('PRODUCT', 'SERVICE') NOT NULL,
    item_id BIGINT NOT NULL,
    used_quantity INT NOT NULL DEFAULT 0,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT,
    CONSTRAINT fk_usage_subscription FOREIGN KEY (customer_subscription_id) REFERENCES customer_subscription(id) ON DELETE CASCADE,
    UNIQUE KEY uk_subscription_item (customer_subscription_id, item_type, item_id),
    INDEX idx_usage_subscription (customer_subscription_id),
    INDEX idx_usage_item (item_type, item_id)
);
