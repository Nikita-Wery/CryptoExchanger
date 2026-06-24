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
