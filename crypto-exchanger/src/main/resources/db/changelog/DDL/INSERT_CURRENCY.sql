INSERT INTO Currencies (
    code,
    name,
    amount,
    description,
    role,
    type,
    is_active
) VALUES (
             ?, ?, ?, ?, ?, ?, ?
         )
    RETURNING currency_id;
