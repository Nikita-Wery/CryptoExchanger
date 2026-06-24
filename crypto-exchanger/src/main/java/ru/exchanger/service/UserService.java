package ru.exchanger.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.exchanger.dto.UserDto;
import ru.exchanger.entities.User;
import ru.exchanger.repository.UsersRepository;
import ru.exchanger.repository.usersrepositoryimpl.UsersRepositoryJDBCImpl;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.Optional;

public class UserService {

    private UsersRepository usersRepository;

    private PasswordEncoder passwordEncoder;

    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        passwordEncoder = new BCryptPasswordEncoder(12);
    }

    private String hashPassword(String pass) {
        return passwordEncoder.encode(pass);
    }

    public boolean registerUser(String email, String password) throws SQLException {
        if (usersRepository.getByEmail(email).isPresent()) {
            return false;
        } else {
            User user = User.builder()
                    .email(email)
                    .passwordHash(hashPassword(password))
                    .role("user")
                    .createdAt(OffsetDateTime.now())
                    .lastLogin(OffsetDateTime.now())
                    .build();
            usersRepository.save(user);
            return true;
        }
    }

    private boolean verify(String password, String hash) {
        return passwordEncoder.matches(password, hash);
    }

    public Optional<UserDto> login(String email, String password) throws SQLException {
        Optional<UserDto> userDto = usersRepository.getByEmail(email);
        if (userDto.isPresent() && verify(password, userDto.get().getPasswordHash())) {
            return userDto;
        }
        return Optional.empty();
    }

    public boolean findByEmail(String email) throws SQLException {
        UsersRepositoryJDBCImpl usersRepositoryJDBC = (UsersRepositoryJDBCImpl) usersRepository;
        return usersRepositoryJDBC.findByEmail(email);
    }
}
