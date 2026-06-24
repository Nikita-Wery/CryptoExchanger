package ru.exchanger.repository.filerepositoryimpl;

import ru.exchanger.entities.FileInfo;
import ru.exchanger.repository.FilesRepository;
import ru.exchanger.utils.SQLoader;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class FilesRepositoryImpl implements FilesRepository {

    private DataSource dataSource;

    private final String pathToInsertFile = "db/changelog/DDL/INSERT_FILE.sql";

    private final String pathToSelectFileById = "db/changelog/DDL/SELECT_FILE_BY_ID.sql";

    public FilesRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<FileInfo> findById(Long id) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     SQLoader.loadSQL(pathToSelectFileById))) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    FileInfo fileInfo = FileInfo.builder()
                            .id(rs.getLong("id"))
                            .originalFileName(rs.getString("original_file_name"))
                            .storageFileName(rs.getString("storage_file_name"))
                            .size(rs.getLong("size"))
                            .type(rs.getString("type"))
                            .build();

                    return Optional.of(fileInfo);
                }
            }
            return Optional.empty();
        }
    }

    @Override
    public void save(FileInfo fileInfo) throws SQLException {
        try (Connection connection = dataSource.getConnection();
                 PreparedStatement ps = connection.prepareStatement(
                 SQLoader.loadSQL(pathToInsertFile))) {

            ps.setString(1, fileInfo.getStorageFileName());
            ps.setString(2, fileInfo.getOriginalFileName());
            ps.setString(3, fileInfo.getType());
            ps.setObject(4, fileInfo.getSize());   // OffsetDateTime

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Получаем UUID из БД и jdbc каситит его к классу UUID.class
                    fileInfo.setId(rs.getLong("id")); // UUID от БД
                }
            }
        }
    }
}
