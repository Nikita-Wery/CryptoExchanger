INSERT INTO Wallet (
    user_id,
    currency_id,
    label,
    wallet_number,
    balance,
    created_at
) VALUES (?, ?, ?, ?, ?, ?)
    RETURNING wallet_id;
