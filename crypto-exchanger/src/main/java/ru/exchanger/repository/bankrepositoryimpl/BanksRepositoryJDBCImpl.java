package ru.exchanger.repository.bankrepositoryimpl;

import ru.exchanger.entities.Bank;
import ru.exchanger.repository.BanksRepository;
import ru.exchanger.utils.SQLoader;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BanksRepositoryJDBCImpl implements BanksRepository {

    private final DataSource dataSource;
    private final String pathToInsert = "db/changelog/DDL/INSERT_BANK.sql";
    private final String pathToSelectAll = "db/changelog/DDL/SELECT_ALL_BANKS.sql";

    public BanksRepositoryJDBCImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Bank bank) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLoader.loadSQL(pathToInsert))) {

            ps.setString(1, bank.getName());
            ps.setString(2, bank.getNumberFormat());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    bank.setBankId(rs.getLong("bank_id"));
                }
            }
        }
    }

    @Override
    public List<Bank> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(SQLoader.loadSQL(pathToSelectAll))) {

            List<Bank> list = new ArrayList<>();
            while (rs.next()) {
                Bank bank = Bank.builder()
                        .bankId(rs.getLong("bank_id"))
                        .name(rs.getString("name"))
                        .numberFormat(rs.getString("number_format"))
                        .build();
                list.add(bank);
            }
            return list;
        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка при загрузке banks из БД", e);
        }
    }
}
