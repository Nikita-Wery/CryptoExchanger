INSERT INTO Banks (
    name,
    number_format
) VALUES (?, ?)
    RETURNING bank_id;
