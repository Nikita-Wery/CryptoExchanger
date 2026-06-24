package ru.exchanger.repository;

import ru.exchanger.entities.UserKYC;

import java.util.List;

public interface UsersKYCRepository extends CrudeRepository<UserKYC> {
    List<UserKYC> findAll();
}
