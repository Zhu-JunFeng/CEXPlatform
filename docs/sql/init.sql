-- 创建用户表
CREATE TABLE IF NOT EXISTS t_user (
    id BIGINT PRIMARY KEY,
    username VARCHAR(64) UNIQUE NOT NULL,
    email VARCHAR(128),
    password VARCHAR(255) NOT NULL,
    real_name VARCHAR(128),
    id_card VARCHAR(18),
    auth_status INTEGER DEFAULT 0,
    status INTEGER DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remark TEXT
);

-- 创建钱包表
CREATE TABLE IF NOT EXISTS t_wallet (
    id BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    currency VARCHAR(32) NOT NULL,
    available_balance NUMERIC(20,8) DEFAULT 0,
    frozen_balance NUMERIC(20,8) DEFAULT 0,
    total_balance NUMERIC(20,8) DEFAULT 0,
    address VARCHAR(255),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user_id, currency),
    FOREIGN KEY (user_id) REFERENCES t_user(id)
);

-- 创建订单表
CREATE TABLE IF NOT EXISTS t_order (
    id BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    symbol VARCHAR(32) NOT NULL,
    order_type INTEGER NOT NULL,
    status INTEGER DEFAULT 0,
    price NUMERIC(20,8),
    quantity NUMERIC(20,8) NOT NULL,
    filled_quantity NUMERIC(20,8) DEFAULT 0,
    fee NUMERIC(20,8) DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES t_user(id)
);

-- 创建成交记录表
CREATE TABLE IF NOT EXISTS t_trade_log (
    id BIGINT PRIMARY KEY,
    buy_order_id BIGINT,
    sell_order_id BIGINT,
    symbol VARCHAR(32) NOT NULL,
    price NUMERIC(20,8) NOT NULL,
    quantity NUMERIC(20,8) NOT NULL,
    trade_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建索引
CREATE INDEX IF NOT EXISTS idx_user_username ON t_user(username);
CREATE INDEX IF NOT EXISTS idx_wallet_user_id ON t_wallet(user_id);
CREATE INDEX IF NOT EXISTS idx_order_user_id ON t_order(user_id);
CREATE INDEX IF NOT EXISTS idx_order_symbol ON t_order(symbol);
CREATE INDEX IF NOT EXISTS idx_order_status ON t_order(status);
CREATE INDEX IF NOT EXISTS idx_trade_symbol ON t_trade_log(symbol);
