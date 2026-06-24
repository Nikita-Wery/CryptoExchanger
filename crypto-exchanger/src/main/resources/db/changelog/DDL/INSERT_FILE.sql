INSERT INTO File(
    storage_file_name,
    original_file_name,
    type,
    size
) VALUES (?, ?, ?, ?)
    RETURNING id;
