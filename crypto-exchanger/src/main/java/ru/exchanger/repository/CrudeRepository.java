package ru.exchanger.repository;

import java.sql.SQLException;

public interface CrudeRepository<T> {
    void save(T entity) throws SQLException;
}
