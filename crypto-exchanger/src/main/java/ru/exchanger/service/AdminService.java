package ru.exchanger.service;

import ru.exchanger.entities.Currency;
import ru.exchanger.repository.CurrenciesRepository;
import ru.exchanger.repository.UsersRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class AdminService {

    private UsersRepository usersRepository;

    private CurrenciesRepository currenciesRepository;

    public AdminService(CurrenciesRepository currenciesRepository, UsersRepository usersRepository) {
        this.currenciesRepository = currenciesRepository;
        this.usersRepository = usersRepository;
    }

    public List<Currency> getAllCurrency() throws SQLException {
        return currenciesRepository.findAll();
    }

    public Long getTotalUserCount() throws SQLException {
        return (long) ((Math.random() * 10) + 1);
        //TODO: пока запускается только на локальной машине, использую RANDOM
//        return usersRepository.getTotalUsersCount();
    }
}
