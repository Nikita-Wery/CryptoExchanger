-- Таблица валют
CREATE TABLE Currencies (
    currency_id SERIAL PRIMARY KEY,
    currency_code VARCHAR(50) NOT NULL UNIQUE,  -- BTC, ETH, RUB
    currency_name VARCHAR(50) NOT NULL,         -- Bitcoin, Ethereum, Рубль
    amount NUMERIC(32,8) NOT NULL DEFAULT 0,  -- объём резерва валюты
    limit_min NUMERIC(20,8) DEFAULT 0,
    limit_max NUMERIC(20,8),
    description TEXT,
    role VARCHAR(50) NOT NULL DEFAULT 'for_sale'
        CHECK (role IN ('for_sale', 'for_buy', 'both', 'frozen')),
    type VARCHAR(50) NOT NULL CHECK (type IN ('crypto', 'fiat')),
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);
