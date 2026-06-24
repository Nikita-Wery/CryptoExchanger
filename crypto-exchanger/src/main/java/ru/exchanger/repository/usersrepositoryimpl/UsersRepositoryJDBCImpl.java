package ru.exchanger.repository.usersrepositoryimpl;

import ru.exchanger.dto.UserDto;
import ru.exchanger.entities.User;
import ru.exchanger.repository.UsersRepository;
import ru.exchanger.utils.SQLoader;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

public class UsersRepositoryJDBCImpl implements UsersRepository {

    private DataSource dataSource;

    // Путь до sql скрипта для вставки 1ого пользователя
    private final String pathToInsertUserScript = "db/changelog/DDL/INSERT_USER.sql";

    // Путь до sql скрипта для получения всех user
    private final String pathToSelectAllUsersScript = "db/changelog/DDL/SELECT_ALL_USERS.sql";

    private final String pathToCheckUserExistsByEmail = "db/changelog/DDL/CHECK_USER_BY_EMAIL.sql";

    private final String pathToSelectUserByEmail = "db/changelog/DDL/SELECT_USER_BY_EMAIL.sql";

    private final String pathToGetTotalUsersCount = "db/changelog/DDL/GET_TOTAL_USER_COUNT.sql";

    public UsersRepositoryJDBCImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(User user) throws SQLException {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(
                SQLoader.loadSQL(pathToInsertUserScript))) {

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getRole());
            ps.setObject(4, user.getCreatedAt());   // OffsetDateTime
            ps.setObject(5, user.getLastLogin());   // OffsetDateTime или null

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Получаем UUID из БД и jdbc каситит его к классу UUID.class
                    user.setId(rs.getObject("id", java.util.UUID.class)); // UUID от БД
                }
            }
        }

    }

    @Override
    public List<User> findAll() throws SQLException {
        try (Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQLoader.loadSQL(pathToSelectAllUsersScript))) {
            List<User> result = new ArrayList<>();

            while (resultSet.next()) {
                User user = User.builder()
                        .id(resultSet.getObject("id", java.util.UUID.class))
                        .email(resultSet.getString("email"))
                        .passwordHash(resultSet.getString("password_hash"))
                        .role(resultSet.getString("role"))
                        .createdAt(resultSet.getObject("created_at", OffsetDateTime.class))
                        .lastLogin(resultSet.getObject("last_login", OffsetDateTime.class))
                        .build();
                result.add(user);
            }
            throw new IllegalStateException("Ошибка при загрузке user из БД");
        }
    }

    @Override
    public Optional<UserDto> getByEmail(String email) throws SQLException {

        try (Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(SQLoader.loadSQL(pathToSelectUserByEmail))) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UserDto userDto = UserDto.builder()
                            .id(rs.getObject("id", java.util.UUID.class))
                            .email(rs.getString("email"))
                            .passwordHash(rs.getString("password_hash"))
                            .role(rs.getString("role").toUpperCase())
                            .build();

                    return Optional.of(userDto);
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    public boolean findByEmail(String email) throws SQLException {
        boolean result = false;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQLoader.loadSQL(pathToCheckUserExistsByEmail))) {

            // Установка параметра
            pstmt.setString(1, email);

            // Выполнение запроса
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    boolean userExists = rs.getBoolean("user_exists");
                    result = userExists;
                }
            }
        }
        return result;
    }

    public Long getTotalUsersCount() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLoader.loadSQL(pathToGetTotalUsersCount));
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getLong("user_count");
            }
            return 0L;
        }
    }
}
