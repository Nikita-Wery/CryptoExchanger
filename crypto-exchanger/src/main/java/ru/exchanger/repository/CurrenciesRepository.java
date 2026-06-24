package ru.exchanger.repository;

import ru.exchanger.dto.CurrencyForBuyDto;
import ru.exchanger.dto.CurrencyForSellDto;
import ru.exchanger.entities.Currency;

import java.sql.SQLException;
import java.util.List;

public interface CurrenciesRepository extends CrudeRepository<Currency> {
    List<Currency> findAll() throws SQLException;

    List<CurrencyForSellDto> getCurrencyForSell() throws SQLException;

    List<CurrencyForBuyDto> getCurrencyForBuy() throws SQLException;

    String getCurrencyName(String code) throws SQLException;
}
