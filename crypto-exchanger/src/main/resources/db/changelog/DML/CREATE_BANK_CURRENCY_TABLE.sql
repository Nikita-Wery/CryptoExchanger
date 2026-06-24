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
