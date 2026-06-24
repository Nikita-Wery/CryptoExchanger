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
