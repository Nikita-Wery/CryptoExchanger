package ru.exchanger.repository;

import ru.exchanger.entities.FileInfo;

import java.sql.SQLException;
import java.util.Optional;

public interface FilesRepository extends CrudeRepository<FileInfo> {
    Optional<FileInfo> findById(Long id) throws SQLException;
}
