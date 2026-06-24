package ru.exchanger.repository;

import ru.exchanger.entities.Order;

import java.util.List;

public interface OrdersRepository extends CrudeRepository<Order> {
    List<Order> findAll();
}
