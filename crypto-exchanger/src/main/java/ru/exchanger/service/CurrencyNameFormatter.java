package ru.exchanger.service;

import ru.exchanger.dto.CurrencyForBuyDto;
import ru.exchanger.dto.CurrencyForSellDto;

import java.util.List;

public class CurrencyNameFormatter {

    // Обрабатывает только валюты на продажу
    public static void capitalizeSellNames(List<CurrencyForSellDto> sellList) {
        if (sellList == null) return;

        sellList.stream()
                .filter(dto -> dto.getName() != null && !dto.getName().isEmpty())
                .forEach(dto -> dto.setName(capitalize(dto.getName())));
    }

    // Обрабатывает только валюты на покупку
    public static void capitalizeBuyNames(List<CurrencyForBuyDto> buyList) {
        if (buyList == null) return;

        buyList.stream()
                .filter(dto -> dto.getName() != null && !dto.getName().isEmpty())
                .forEach(dto -> dto.setName(capitalize(dto.getName())));
    }

    // Метод приведения первой буквы к заглавной
    private static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
