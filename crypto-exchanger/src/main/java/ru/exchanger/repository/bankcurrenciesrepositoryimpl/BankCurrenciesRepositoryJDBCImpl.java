package ru.exchanger.repository.bankcurrenciesrepositoryimpl;

import ru.exchanger.entities.BankCurrency;
import ru.exchanger.repository.BankCurrenciesRepository;
import ru.exchanger.utils.SQLoader;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankCurrenciesRepositoryJDBCImpl implements BankCurrenciesRepository {

    private final DataSource dataSource;
    private final String pathToInsert = "db/changelog/DDL/INSERT_BANK_CURRENCY.sql";
    private final String pathToSelectAll = "db/changelog/DDL/SELECT_ALL_BANKS_CURRENCIES.sql";

    public BankCurrenciesRepositoryJDBCImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(BankCurrency bc) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLoader.loadSQL(pathToInsert))) {

            ps.setInt(1, bc.getBankId());
            ps.setInt(2, bc.getCurrencyId());
            ps.setBigDecimal(3, bc.getFeePercent());
            ps.setBigDecimal(4, bc.getLimitMin());
            ps.setBigDecimal(5, bc.getLimitMax());

            ps.executeUpdate(); // composite PK — ничего дополнительно не возвращаем
        }
    }

    @Override
    public List<BankCurrency> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(SQLoader.loadSQL(pathToSelectAll))) {

            List<BankCurrency> list = new ArrayList<>();
            while (rs.next()) {
                BankCurrency bc = BankCurrency.builder()
                        .bankId(rs.getInt("bank_id"))
                        .currencyId(rs.getInt("currency_id"))
                        .feePercent(rs.getBigDecimal("fee_percent"))
                        .limitMin(rs.getBigDecimal("limit_min"))
                        .limitMax(rs.getBigDecimal("limit_max"))
                        .build();
                list.add(bc);
            }
            return list;
        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка при загрузке bank_currency из БД", e);
        }
    }
}
