package ru.exchanger.repository.ordersrepositoryimpl;

import ru.exchanger.entities.Order;
import ru.exchanger.repository.OrdersRepository;
import ru.exchanger.utils.SQLoader;

import javax.sql.DataSource;
import java.sql.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrdersRepositoryJDBCImpl implements OrdersRepository {

    private final DataSource dataSource;

    private final String pathToInsertOrderScript = "db/changelog/DDL/INSERT_ORDER.sql";

    private final String pathToSelectAllOrdersScript = "db/changelog/DDL/SELECT_ALL_ORDERS.sql";

    public OrdersRepositoryJDBCImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Order order) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLoader.loadSQL(pathToInsertOrderScript))) {

            ps.setObject(1, order.getUserId());
            ps.setInt(2, order.getCurrencySellId());
            ps.setInt(3, order.getCurrencyBuyId());
            ps.setBigDecimal(4, order.getAmountSell());
            ps.setBigDecimal(5, order.getAmountBuy());
            ps.setString(6, order.getStatus());
            ps.setObject(7, order.getCreatedAt());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    order.setOrderId(rs.getLong("order_id"));
                }
            }
        }
    }

    @Override
    public List<Order> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQLoader.loadSQL(pathToSelectAllOrdersScript))) {

            List<Order> result = new ArrayList<>();

            while (resultSet.next()) {
                Order order = Order.builder()
                        .orderId(resultSet.getLong("order_id"))
                        .userId(resultSet.getObject("user_id", UUID.class))
                        .currencySellId(resultSet.getInt("currency_sell_id"))
                        .currencyBuyId(resultSet.getInt("currency_buy_id"))
                        .amountSell(resultSet.getBigDecimal("amount_sell"))
                        .amountBuy(resultSet.getBigDecimal("amount_buy"))
                        .status(resultSet.getString("status"))
                        .createdAt(resultSet.getObject("created_at", OffsetDateTime.class))
                        .build();
                result.add(order);
            }
            return result;
        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка при загрузке orders из БД", e);
        }
    }
}
