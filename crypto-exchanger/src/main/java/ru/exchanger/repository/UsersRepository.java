package ru.exchanger.repository;

import ru.exchanger.dto.UserDto;
import ru.exchanger.entities.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UsersRepository extends CrudeRepository<User> {
    List<User> findAll() throws SQLException;

    Optional<UserDto> getByEmail(String email) throws SQLException;

    Long getTotalUsersCount() throws SQLException;
}
