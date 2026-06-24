package ru.exchanger.service;

import org.json.JSONException;
import org.json.JSONObject;
import ru.exchanger.dto.CurrencyForBuyDto;
import ru.exchanger.dto.CurrencyForSellDto;
import ru.exchanger.repository.CurrenciesRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class CurrencyService {

    private CurrenciesRepository currenciesRepository;

    public CurrencyService(CurrenciesRepository currenciesRepository) {
        this.currenciesRepository = currenciesRepository;
    }

    public List<CurrencyForBuyDto> getCurrencyForBuy() throws SQLException {
        List<CurrencyForBuyDto> list = currenciesRepository.getCurrencyForBuy();

        list.stream()
                .filter(dto -> dto.getName() != null && !dto.getName().isEmpty())
                .forEach(dto -> dto.setName(capitalize(dto.getName())));

        return list;
    }

    public List<CurrencyForSellDto> getCurrencyForSell() throws SQLException {
        List<CurrencyForSellDto> currencyForSellDtos = currenciesRepository.getCurrencyForSell();
            return currencyForSellDtos.stream()
            .map(currency -> {
                currency.setName(capitalize(currency.getName()));
                if (currency.getCode().equals("RUB") || currency.getCode().equals("USD")) {
                    String effectiveCode = currency.getCode() + "_" +
                        currency.getBankName().toUpperCase().replace(" ", "_");
                    currency.setEffectiveCode(effectiveCode);
                    currency.setName(currency.getBankName());
                } else {
                    currency.setEffectiveCode(currency.getCode());
                }
                return currency;
            }).collect(Collectors.toList());
    }

    public BigDecimal getQuantityByPrice(String sellCurrencyCode, String buyCurrencyCode)
            throws SQLException, IOException, InterruptedException {

        if (sellCurrencyCode == null || buyCurrencyCode == null) {
            throw new IllegalArgumentException("Unknown currency code");
        }

        String currencyBuyNameKey = currenciesRepository.getCurrencyName(buyCurrencyCode);
        String currencySellNameKey = currenciesRepository.getCurrencyName(sellCurrencyCode);

        String url = "https://api.coingecko.com/api/v3/simple/price"
                + "?ids=" + currencyBuyNameKey + "," + currencySellNameKey
                + "&vs_currencies=usd";

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        int maxRetries = 3; // Максимальное количество попыток
        int attempt = 0;

        while (attempt < maxRetries) {
            try {
                attempt++;
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                JSONObject json = new JSONObject(response.body());

                double price1 = json.getJSONObject(currencySellNameKey).getDouble("usd");
                double price2 = json.getJSONObject(currencyBuyNameKey).getDouble("usd");

                return BigDecimal.valueOf(price1 / price2);
            } catch (IOException | InterruptedException | JSONException e) {
                System.out.println("Попытка " + attempt + " не удалась: " + e.getMessage());
                if (attempt >= maxRetries) {
                    throw e; // Если все попытки исчерпаны, пробрасываем исключение
                }
                Thread.sleep(1000); // Подождать 1 секунду перед следующей попыткой
            }
        }

        // На всякий случай, хотя цикл должен вернуть результат или бросить исключение
        throw new IOException("Не удалось получить курс после " + maxRetries + " попыток");
    }


    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}

