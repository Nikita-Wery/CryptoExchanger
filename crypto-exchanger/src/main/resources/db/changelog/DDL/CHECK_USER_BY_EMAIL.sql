SELECT EXISTS(
    SELECT 1
    FROM Users
    WHERE email = ?
) AS user_exists;