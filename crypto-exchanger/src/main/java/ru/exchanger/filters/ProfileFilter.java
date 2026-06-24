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

@WebFilter("/profile/*")
public class ProfileFilter implements Filter {

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

        boolean isRegisteredEarlier = cookiesChecker.autoLoginFromCookies(httpRequest, httpResponse);

        if (isRegisteredEarlier) {
            filterChain.doFilter(servletRequest,servletResponse);
        } else {
            try {
                if (userService.findByEmail(httpRequest.getParameter("email"))) {
                    httpResponse.sendRedirect("/login");
                } else {
                    httpResponse.sendRedirect("/register");
                }
            } catch (SQLException e) {
                // TODO: метод для отладки
                System.out.println("Ошибка проверки наличия пользователя по почте");
                httpResponse.sendRedirect("/home");
            }
        }
    }

    @Override
    public void destroy() {

    }
}
