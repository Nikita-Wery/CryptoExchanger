package ru.exchanger.repository.walletsrepositoryimpl;

import ru.exchanger.entities.Wallet;
import ru.exchanger.repository.WalletsRepository;
import ru.exchanger.utils.SQLoader;

import javax.sql.DataSource;
import java.sql.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class WalletsRepositoryJDBCImpl implements WalletsRepository {

    private DataSource dataSource;

    private final String pathToInsertWalletScript = "db/changelog/DDL/INSERT_WALLET.sql";

    private final String pathToSelectAllWalletsScript = "db/changelog/DDL/SELECT_ALL_USERS.sql";

    public WalletsRepositoryJDBCImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Wallet wallet) throws SQLException {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     SQLoader.loadSQL(pathToInsertWalletScript))) {

            ps.setObject(1,  wallet.getUserId());
            ps.setInt(2, wallet.getCurrencyId());
            ps.setString(3, wallet.getLabel());
            ps.setString(4, wallet.getWalletNumber());
            ps.setBigDecimal(5, wallet.getBalance());   // OffsetDateTime
            ps.setObject(6, wallet.getCreatedAt());   // OffsetDateTime

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    wallet.setWalletId(rs.getLong("wallet_id")); // UUID от БД
                }
            }
        }
    }


    @Override
    public List<Wallet> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQLoader.loadSQL(pathToSelectAllWalletsScript))) {
            List<Wallet> result = new ArrayList<>();

            while (resultSet.next()) {
                Wallet wallet = Wallet.builder()
                        .walletId(resultSet.getLong("wallet_id"))
                        .userId(resultSet.getObject("user_id", java.util.UUID.class))
                        .currencyId(resultSet.getInt("currency_id"))
                        .label(resultSet.getString("label"))
                        .walletNumber(resultSet.getString("wallet_number"))
                        .balance(resultSet.getBigDecimal("balance"))
                        .createdAt(resultSet.getObject("created_at", OffsetDateTime.class))
                        .build();
                result.add(wallet);
            }
            return result;
        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка при загрузке wallet из БД");
        }
    }
}
