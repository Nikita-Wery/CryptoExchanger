package ru.exchanger.utils;

import ru.exchanger.dto.UserDto;
import ru.exchanger.service.CurrencyService;
import ru.exchanger.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class CookiesChecker {

    private final UserService userService;

    public CookiesChecker(UserService userService) {
        this.userService = userService;
    }

    public boolean autoLoginFromCookies(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // Получаем cookie с email и password
            Cookie[] cookies = req.getCookies();
            String email = null;
            String password = null;

            if (cookies != null) {
                for (Cookie cookie : cookies) {

                    if ("email".equals(cookie.getName())) {
                        email = cookie.getValue();
                    } else if ("pass".equals(cookie.getName())) {
                        password = cookie.getValue();
                    }
                }
            }

            // Если нашли оба cookie, пытаемся авторизоваться
            if (email != null && password != null) {
                // Вызываем метод авторизации из UserService
                Optional<UserDto> userDto = userService.login(email, password);
                if (userDto.isPresent()) {
                    // Создаем сессию (если её не было) и сохраняем пользователя
                    HttpSession session = req.getSession();
                    if (session == null) {
                        session = req.getSession(true);
                    }
                    session.setAttribute("email", email);
                    System.out.println("Auto-login successful for user: " + email);
                    return true;
                } else {
                    // Если авторизация не удалась, удаляем невалидные cookie
                    clearAuthCookies(resp);
                }
            }
        } catch (Exception e) {
            System.err.println("Error during auto-login: " + e.getMessage());
            // В случае ошибки также очищаем cookie
            clearAuthCookies(resp);
        }
        return false;
    }

    private void clearAuthCookies(HttpServletResponse response) {
        // Создаем cookie с максимальным возрастом 0 для удаления
        Cookie emailCookie = new Cookie("email", "");
        emailCookie.setMaxAge(0);
        emailCookie.setPath("/");

        Cookie passwordCookie = new Cookie("pass", "");
        passwordCookie.setMaxAge(0);
        passwordCookie.setPath("/");

        response.addCookie(emailCookie);
        response.addCookie(passwordCookie);
    }
}
