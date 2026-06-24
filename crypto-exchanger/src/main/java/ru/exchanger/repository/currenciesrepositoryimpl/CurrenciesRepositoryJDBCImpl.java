package ru.exchanger.repository.currenciesrepositoryimpl;

import ru.exchanger.dto.CurrencyForSellDto;
import ru.exchanger.entities.Currency;
import ru.exchanger.repository.CurrenciesRepository;
import ru.exchanger.utils.SQLoader;
import ru.exchanger.dto.CurrencyForBuyDto;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurrenciesRepositoryJDBCImpl implements CurrenciesRepository {

    private DataSource dataSource;

    private final String pathToInsertCurrencyScript = "db/changelog/DDL/INSERT_CURRENCY.sql";

    private final String pathToSelectAllCurrenciesScript = "db/changelog/DDL/SELECT_ALL_CURRENCIES.sql";

    private final String pathToSelectCurrenciesForSell = "db/changelog/DDL/SELECT_CURRENCIES_FOR_SELL.sql";

    private final String pathToSelectCurrenciesForBuy = "db/changelog/DDL/SELECT_CURRENCIES_FOR_BUY.sql";

    private final String pathToSelectCurrencyName = "db/changelog/DDL/SELECT_CURRENCY_NAME.sql";

    public CurrenciesRepositoryJDBCImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Currency currency) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     SQLoader.loadSQL(pathToInsertCurrencyScript), Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, currency.getCode());
            ps.setString(2, currency.getName());
            ps.setBigDecimal(3, currency.getAmount());
            ps.setString(4, currency.getDescription());
            ps.setString(5, currency.getRole());
            ps.setString(6, currency.getType());
            ps.setBoolean(7, currency.isActive());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    currency.setCurrencyId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public List<Currency> findAll() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQLoader.loadSQL(pathToSelectAllCurrenciesScript))) {

            List<Currency> result = new ArrayList<>();

            while (resultSet.next()) {
                Currency currency = Currency.builder()
                        .currencyId(resultSet.getInt("currency_id"))
                        .code(resultSet.getString("currency_code"))
                        .name(resultSet.getString("currency_name"))
                        .amount(resultSet.getBigDecimal("amount").stripTrailingZeros())
                        .description(resultSet.getString("description"))
                        .role(resultSet.getString("role"))
                        .type(resultSet.getString("type"))
                        .isActive(resultSet.getBoolean("is_active"))
                        .build();
                result.add(currency);
            }

            return result;
        }
    }

    @Override
    public List<CurrencyForBuyDto> getCurrencyForBuy() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQLoader.loadSQL(pathToSelectCurrenciesForBuy))) {

            List<CurrencyForBuyDto> result = new ArrayList<>();

            while (resultSet.next()) {
                CurrencyForBuyDto currencyForBuyDTO = CurrencyForBuyDto.builder()
                        .currencyId(resultSet.getInt("currency_id"))
                        .code(resultSet.getString("currency_code"))
                        .name(resultSet.getString("currency_name"))
                        .amount(resultSet.getBigDecimal("amount"))
                        .build();
                result.add(currencyForBuyDTO);
            }

            return result;
        }
    }

    @Override
    public String getCurrencyName(String code) throws SQLException {

        String result = "";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     SQLoader.loadSQL(pathToSelectCurrencyName))) {

            ps.setString(1, code);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = rs.getString("currency_name");
                }
            }
        }

        if (!result.isEmpty()) {
            return result;
        } else {
            throw new SQLException("Currency not found for code: " + code);
        }
    }

    @Override
    public List<CurrencyForSellDto> getCurrencyForSell() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQLoader.loadSQL(pathToSelectCurrenciesForSell))) {

            List<CurrencyForSellDto> result = new ArrayList<>();

            while (resultSet.next()) {
                CurrencyForSellDto currencyForSellDto = CurrencyForSellDto.builder()
                        .currencyId(resultSet.getInt("currency_id"))
                        .code(resultSet.getString("currency_code"))
                        .name(resultSet.getString("currency_name"))
                        .bankId(resultSet.getInt("bank_id"))      // для крипты будет NULL / 0
                        .bankName(resultSet.getString("bank_name")) // для крипты будет NULL
                        .build();
                result.add(currencyForSellDto);
            }
            return result;
        }
    }
}
