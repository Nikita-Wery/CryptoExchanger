SELECT
    c.currency_id,
    c.currency_code,
    c.currency_name AS currency_name,
    c.limit_min,
    c.limit_max,
    b.bank_id,
    b.bank_name AS bank_name
FROM Currencies c
LEFT JOIN BankCurrency bc ON bc.currency_id = c.currency_id
LEFT JOIN Banks b ON b.bank_id = bc.bank_id
WHERE c.role IN ('for_buy', 'both');