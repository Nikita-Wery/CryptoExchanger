package ru.exchanger.servltes;

import ru.exchanger.dto.CurrencyForBuyDto;
import ru.exchanger.dto.CurrencyForSellDto;
import ru.exchanger.service.CurrencyService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@WebServlet(value="/home", loadOnStartup=1)
public class RootServlet extends HttpServlet {

    private CurrencyService currencyService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        currencyService = (CurrencyService) config.getServletContext().getAttribute("currencyService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<CurrencyForSellDto> sellDtos = currencyService.getCurrencyForSell();
            List<CurrencyForBuyDto> buyDtos = currencyService.getCurrencyForBuy();

            req.setAttribute("currenciesForSell", sellDtos);
            req.setAttribute("currenciesForBuy", buyDtos);

            // Получаем exchangeRate из сессии, если он был установлен после POST
            BigDecimal rate = (BigDecimal) req.getSession().getAttribute("exchangeRate");
            if(rate == null) {
                rate = currencyService.getQuantityByPrice(sellDtos.getFirst().getCode(), buyDtos.getFirst().getCode());
            }
            req.setAttribute("exchangeRate", rate);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Ошибка получения валют через API");
        }

        req.getRequestDispatcher("/jsp/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        BigDecimal rate = (BigDecimal) req.getSession().getAttribute("exchangeRate");

        // Получаем данные о выбранных валютах и суммах
        String currencyGive = req.getParameter("currencyGive");
        String currencyReceive = req.getParameter("currencyReceive");
        String amountGiveStr = req.getParameter("amountGive");
        String amountReceiveStr = req.getParameter("amountReceive");

        String email = req.getParameter("email");
        String fio = req.getParameter("fio");
        String card = req.getParameter("card");


        if (email != null && fio != null && card != null) {
            req.getSession().setAttribute("email", email);
            req.getSession().setAttribute("fio", fio);
            req.getSession().setAttribute("card", card);
            req.getSession().setAttribute("status", "approved");

            resp.sendRedirect(req.getContextPath() + "/transaction");
        } else {
            System.out.println("POST -> " + currencyGive + " / " + currencyReceive);
            System.out.println("VALUTES -> " + amountGiveStr + " / " + amountReceiveStr);
            System.out.println("LOG ->" + email + " / " + fio + " / " + card);

            // Здесь логика вычисления exchangeRate
            if(currencyGive != null && currencyReceive != null) {
                try {
                    rate = currencyService.getQuantityByPrice(currencyGive,currencyReceive);

                    // Сохраняем rate в сессии, чтобы GET после redirect видел его
                    req.getSession().setAttribute("exchangeRate", rate);
                } catch (SQLException e) {
                    System.out.println("Ошибка получения имени криптовалюты из бд");
                } catch (InterruptedException e) {
                    System.out.println("Ошибка получения валют через API");
                }
            }


            // Сохраняем текущие выбранные валюты и суммы в сессии, чтобы подсветка и поля оставались
            req.getSession().setAttribute("selectedGive", currencyGive);
            req.getSession().setAttribute("selectedReceive", currencyReceive);

            // После POST делаем redirect на GET, чтобы страница обновилась с новыми данными
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }
}
