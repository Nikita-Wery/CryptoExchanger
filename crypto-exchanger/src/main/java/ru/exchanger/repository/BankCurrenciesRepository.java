package ru.exchanger.repository;

import ru.exchanger.entities.BankCurrency;

import java.util.List;

public interface BankCurrenciesRepository extends CrudeRepository<BankCurrency> {
    List<BankCurrency> findAll();
}
