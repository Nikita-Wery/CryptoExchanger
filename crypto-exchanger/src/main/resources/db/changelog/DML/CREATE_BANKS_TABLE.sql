-- Таблица доступных банков (fiat)
CREATE TABLE Banks (
       bank_id SERIAL PRIMARY KEY,
       bank_name VARCHAR(50) NOT NULL UNIQUE,
       number_format TEXT NOT NULL  -- формат карты банка, количество цифр и т.п.
);
