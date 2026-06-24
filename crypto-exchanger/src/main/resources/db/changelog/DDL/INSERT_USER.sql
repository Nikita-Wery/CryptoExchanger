-- Вставка нового пользователя
INSERT INTO Users (id, email, password_hash, role, created_at, last_login)
VALUES (gen_random_uuid(), ?, ?, ?, ?, ?)
    RETURNING id;
