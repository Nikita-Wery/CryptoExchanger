CREATE TABLE File(
    id SERIAL primary key,
    storage_file_name TEXT NOT NULL,
    original_file_name TEXT NOT NULL,
    type varchar(20) NOT NULL,
    size bigint NOT NULL
);