package ru.exchanger.filters;

import ru.exchanger.entities.Role;
import ru.exchanger.service.AdminService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/adminpanel")
public class AdminFilter implements Filter {

    private AdminService service;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        service = (AdminService) filterConfig.getServletContext().getAttribute("adminService");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession(false);

        if (session != null) {
            String role = (String) session.getAttribute("role");
            if (role != null && Role.valueOf(role.toUpperCase()) == Role.ADMIN) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
        httpResponse.sendRedirect("/login");
    }

    @Override
    public void destroy() {

    }
}
