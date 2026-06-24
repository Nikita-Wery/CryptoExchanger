CREATE TABLE Users (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       email VARCHAR(254) NOT NULL UNIQUE,
                       password_hash TEXT NOT NULL,
                       role VARCHAR(50) NOT NULL CHECK (role IN ('admin', 'user', 'moderator')),
                       created_at TIMESTAMPTZ DEFAULT now(),
                       last_login TIMESTAMPTZ
);

-- Таблица личных данных пользователя (KYC)
CREATE TABLE User_KYC (
                          user_id UUID PRIMARY KEY,
                          first_name VARCHAR(50) NOT NULL,
                          second_name VARCHAR(50) NOT NULL,
                          patronymic_name VARCHAR(50) NOT NULL,
                          date_of_birth DATE NOT NULL,
                          passport_number VARCHAR(20) NOT NULL,
                          passport_series VARCHAR(10) NOT NULL,
                          FOREIGN KEY (user_id)
                              REFERENCES Users (id)
                              ON DELETE CASCADE
);

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

-- Таблица кошельков, один кошелёк = одна валюта
CREATE TABLE Wallets (
                         wallet_id SERIAL PRIMARY KEY,
                         user_id UUID NOT NULL,
                         currency_id INT NOT NULL,
                         label TEXT DEFAULT 'User wallet',
                         wallet_number TEXT NOT NULL, -- Номер криптокошелька или банковской карты
                         balance NUMERIC(32,8) DEFAULT 0,
                         created_at TIMESTAMPTZ DEFAULT now(),
                         FOREIGN KEY (user_id)
                             REFERENCES Users (id)
                             ON DELETE CASCADE,
                         FOREIGN KEY (currency_id)
                             REFERENCES Currencies (currency_id)
                             ON DELETE RESTRICT
);

-- Таблица доступных банков (fiat)
CREATE TABLE Banks (
                       bank_id SERIAL PRIMARY KEY,
                       bank_name VARCHAR(50) NOT NULL UNIQUE,
                       number_format TEXT NOT NULL  -- формат карты банка, количество цифр и т.п.
);

-- Таблица связи банков и валют (M2M)
CREATE TABLE BankCurrency (
                              bank_id INT NOT NULL,
                              currency_id INT NOT NULL,
                              fee_percent NUMERIC(5,2) DEFAULT 0, -- комиссия банка
                              PRIMARY KEY (bank_id, currency_id),
                              FOREIGN KEY (bank_id)
                                  REFERENCES Banks (bank_id)
                                  ON DELETE CASCADE,
                              FOREIGN KEY (currency_id)
                                  REFERENCES Currencies (currency_id)
                                  ON DELETE CASCADE
);

-- Таблица совершённых сделок
CREATE TABLE Orders (
                        order_id SERIAL PRIMARY KEY,
                        user_id UUID NOT NULL,
                        currency_sell_id INT NOT NULL,
                        currency_buy_id INT NOT NULL,
                        amount_sell NUMERIC(32,8) NOT NULL,
                        amount_buy NUMERIC(32,8) NOT NULL,
                        status VARCHAR(50) NOT NULL DEFAULT 'pending'
                            CHECK (status IN ('pending', 'processing', 'completed', 'cancelled', 'failed')),
                        created_at TIMESTAMPTZ DEFAULT now(),
                        FOREIGN KEY (user_id)
                            REFERENCES Users (id)
                            ON DELETE CASCADE,
                        FOREIGN KEY (currency_sell_id)
                            REFERENCES Currencies (currency_id)
                            ON DELETE RESTRICT,
                        FOREIGN KEY (currency_buy_id)
                            REFERENCES Currencies (currency_id)
                            ON DELETE RESTRICT
);
