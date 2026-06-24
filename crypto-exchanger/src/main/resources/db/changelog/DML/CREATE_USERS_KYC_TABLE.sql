-- Таблица личных данных пользователя (KYC)
CREATE TABLE User_KYC (
      user_id UUID PRIMARY KEY,
      first_name VARCHAR(50) NOT NULL,
      second_name VARCHAR(50) NOT NULL,
      patronymic_name VARCHAR(50) NOT NULL,
      date_of_birth DATE NOT NULL,
      passport_number VARCHAR(20) NOT NULL,
      passport_series VARCHAR(10) NOT NULL,
      FOREIGN KEY (user_id)
          REFERENCES Users (id)
          ON DELETE CASCADE
);
