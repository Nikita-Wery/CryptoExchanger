package ru.exchanger.servltes;

import ru.exchanger.entities.Currency;
import ru.exchanger.service.AdminService;
import ru.exchanger.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpClient;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/adminpanel")
public class AdminPanel extends HttpServlet {

    private UserService userService;
    private AdminService adminService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        adminService = (AdminService) config.getServletContext().getAttribute("adminService");
        userService = (UserService) config.getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("getAll".equals(action)) {
            try {
                List<Currency> allCurrencies = adminService.getAllCurrency();
                req.setAttribute("currencies", allCurrencies);
                req.getRequestDispatcher("/jsp/adminpanel.jsp").forward(req, resp);
            } catch (SQLException e) {
                e.printStackTrace();
                req.setAttribute("errorMessage", "Ошибка при получении данных о валютах.");
                req.getRequestDispatcher("/jsp/adminpanel.jsp").forward(req, resp);
            }
        } else if ("getUsersCount".equals(action)) {
            try {
                Long count = adminService.getTotalUserCount();
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write("{\"count\":" + count + "}");
            } catch (SQLException e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("{\"count\":0}");
            }
        } else {
            req.getRequestDispatcher("/jsp/adminpanel.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp); // По умолчанию перенаправляем на doGet
    }
}
