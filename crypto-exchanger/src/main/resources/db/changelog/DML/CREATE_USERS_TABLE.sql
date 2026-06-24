CREATE TABLE Users (
   id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
   email VARCHAR(254) NOT NULL UNIQUE,
   password_hash TEXT NOT NULL,
   role VARCHAR(50) NOT NULL CHECK (role IN ('admin', 'user', 'moderator')),
   created_at TIMESTAMPTZ DEFAULT now(),
   last_login TIMESTAMPTZ
);
