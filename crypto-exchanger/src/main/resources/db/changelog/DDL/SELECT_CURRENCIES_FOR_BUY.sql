SELECT
    currency_id,
    currency_code,
    currency_name,
    amount
FROM Currencies
WHERE role IN ('for_sale', 'both');