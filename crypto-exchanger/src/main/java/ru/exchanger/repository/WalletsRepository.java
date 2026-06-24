package ru.exchanger.repository;

import ru.exchanger.entities.Wallet;

import java.util.List;

public interface WalletsRepository extends CrudeRepository<Wallet> {
    List<Wallet> findAll();
}
