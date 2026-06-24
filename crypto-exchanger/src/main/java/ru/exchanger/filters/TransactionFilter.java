package ru.exchanger.filters;

import ru.exchanger.service.UserService;
import ru.exchanger.utils.CookiesChecker;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLOutput;

@WebFilter("/transaction")
public class TransactionFilter implements Filter {

    private UserService userService;

    private CookiesChecker cookiesChecker;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userService = (UserService) filterConfig.getServletContext().getAttribute("userService");
        cookiesChecker = new CookiesChecker(userService);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession(false);

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        Boolean sessionExist = session != null;
        Boolean status = httpRequest.getSession().getAttribute("status") != null;

        if (sessionExist && status) {
            boolean isRegisteredEarlier = cookiesChecker.autoLoginFromCookies(httpRequest, httpResponse);

            //TODO: метдо отладки
            System.out.println("ЕСТЬ КУКИ? " + isRegisteredEarlier);

            if (isRegisteredEarlier) {
                filterChain.doFilter(servletRequest,servletResponse);
            } else {
                try {
                    if (userService.findByEmail(httpRequest.getParameter("email"))) {
                        //TODO: метод отладки
                        System.out.println("ПОЛЬЗОВАТЕЛЬ НАШЁЛСЯ ПО EMAIL");
                        httpResponse.sendRedirect("/login");
                    } else {
                        //TODO: метод для отладки
                        System.out.println("НЕТ ТАКОГО ПОЛЬЗОВАТЕЛЯ ПО EMAIL");
                        httpResponse.sendRedirect("/register");
                    }
                } catch (SQLException e) {
                    System.out.println("Ошибка проверки наличия пользователя по почте");
                    // Чтобы поле email не замораживалось
                    httpRequest.getSession().removeAttribute("status");
                    httpResponse.sendRedirect("/home");
                }
            }
        } else {
            // TODO: Метод для отладки
            System.out.println("CЕССИЯ: " + sessionExist + " СТАТУС:" + status);

            httpResponse.sendRedirect("/home");
        }
    }

    @Override
    public void destroy() {

    }
}
