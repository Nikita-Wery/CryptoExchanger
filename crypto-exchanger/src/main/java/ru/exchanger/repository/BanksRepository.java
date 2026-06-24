package ru.exchanger.repository;

import ru.exchanger.entities.Bank;

import java.util.List;

public interface BanksRepository extends CrudeRepository<Bank> {
    List<Bank> findAll();
}
