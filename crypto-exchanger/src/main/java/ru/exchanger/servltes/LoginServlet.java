package ru.exchanger.servltes;

import ru.exchanger.dto.UserDto;
import ru.exchanger.entities.Role;
import ru.exchanger.service.UserService;
import ru.exchanger.utils.CookiesCreater;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            Optional<UserDto> user = userService.login(email, password);
            if (user.isPresent()) {
                if (Arrays.stream(req.getCookies()).findAny().isEmpty()) {
                    CookiesCreater.createAuthCookies(resp, email, password);
                }

                if (Role.valueOf(user.get().getRole()) == Role.ADMIN) {
                    req.getSession().setAttribute("role", "ADMIN");
                } else {
                    req.getSession().setAttribute("role", "USER");
                }
                resp.sendRedirect("/home");
            } else {
                req.getRequestDispatcher("/jsp/login.jsp").forward(req,resp);
            }

        } catch (SQLException e) {
            System.out.println("Ошбика получения ползователя из базы данных");
            req.getRequestDispatcher("/jsp/login.jsp").forward(req,resp);
        }

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserService) config.getServletContext().getAttribute("userService");
    }
}
