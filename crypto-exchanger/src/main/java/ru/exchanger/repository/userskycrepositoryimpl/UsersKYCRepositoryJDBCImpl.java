package ru.exchanger.repository.userskycrepositoryimpl;

import ru.exchanger.entities.UserKYC;
import ru.exchanger.repository.UsersKYCRepository;
import ru.exchanger.utils.SQLoader;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UsersKYCRepositoryJDBCImpl implements UsersKYCRepository {

    private final DataSource dataSource;

    private final String pathToInsertKycScript = "db/changelog/DDL/INSERT_USER_KYC.sql";

    private final String pathToSelectAllKycScript = "db/changelog/DDL/SELECT_ALL_USER_KYC.sql";

    public UsersKYCRepositoryJDBCImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(UserKYC kyc) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLoader.loadSQL(pathToInsertKycScript))) {

            ps.setObject(1, kyc.getUserId());           // UUID
            ps.setString(2, kyc.getPersonalData());     // JSONB как текст
            ps.setString(3, kyc.getDocuments());        // JSONB как текст
            ps.setString(4, kyc.getFirstName());
            ps.setString(5, kyc.getSecondName());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    kyc.setUserId(rs.getObject("user_id", UUID.class));
                }
            }
        }
    }

    @Override
    public List<UserKYC> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(SQLoader.loadSQL(pathToSelectAllKycScript))) {

            List<UserKYC> list = new ArrayList<>();
            while (rs.next()) {
                UserKYC kyc = UserKYC.builder()
                        .userId(rs.getObject("user_id", UUID.class))
                        .personalData(rs.getString("personal_data"))
                        .documents(rs.getString("documents"))
                        .firstName(rs.getString("first_name"))
                        .secondName(rs.getString("second_name"))
                        .build();
                list.add(kyc);
            }
            return list;
        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка при загрузке User_KYC из БД", e);
        }
    }
}
