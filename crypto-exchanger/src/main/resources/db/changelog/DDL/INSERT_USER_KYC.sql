INSERT INTO User_KYC (
    user_id,
    personal_data,
    documents,
    first_name,
    second_name
) VALUES (?, ?::jsonb, ?::jsonb, ?, ?)
    RETURNING user_id;
