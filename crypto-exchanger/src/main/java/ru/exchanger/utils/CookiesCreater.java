package ru.exchanger.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookiesCreater {

    public static void createAuthCookies(HttpServletResponse response, String email, String password) {
        // Создаем cookie для email
        Cookie emailCookie = new Cookie("email", email);
        // Устанавливаем максимальный срок жизни (примерно 1 год)
        emailCookie.setMaxAge(60 * 60 * 24 * 365); // 31536000 секунд = 1 год
        emailCookie.setPath("/"); // Доступно для всего приложения

        // Создаем cookie для пароля
        Cookie passwordCookie = new Cookie("pass", password);
        passwordCookie.setHttpOnly(true);
        passwordCookie.setSecure(true);

        passwordCookie.setMaxAge(60 * 60 * 24 * 365); // 1 год
        passwordCookie.setPath("/"); // Доступно для всего приложения

        // Добавляем cookie в ответ
        response.addCookie(emailCookie);
        response.addCookie(passwordCookie);

        System.out.println("Cookies created for user: " + email);
    }
}
