INSERT INTO Orders (
    user_id,
    currency_sell_id,
    currency_buy_id,
    amount_sell,
    amount_buy,
    status,
    created_at
) VALUES (
             ?, ?, ?, ?, ?, ?, ?
         )
    RETURNING order_id;
