package ru.exchanger.servltes;

import ru.exchanger.service.UserService;
import ru.exchanger.utils.CookiesCreater;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class SignUp extends HttpServlet {

    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeatPassword");

        if (!password.equals(repeatPassword)) {
            req.setAttribute("error", "Пароли не совпадают");
            req.getRequestDispatcher("/jsp/registration.jsp").forward(req,resp);
            return;
        }

        try {
            if (!userService.findByEmail(email)) {

                userService.registerUser(email, password);

                // Создаем cookie с email и паролем
                CookiesCreater.createAuthCookies(resp, email, password);

                // Перенаправляем на домашнюю страницу
                resp.sendRedirect("/home");
            } else {
                resp.sendRedirect("/login");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка регистрации пользователя в БД");
            req.setAttribute("error", "Ошибка регистрации. Попробуйте еще раз.");
            req.getRequestDispatcher("/jsp/registration.jsp").forward(req,resp);
        }
    }


    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserService) config.getServletContext().getAttribute("userService");
    }
}
