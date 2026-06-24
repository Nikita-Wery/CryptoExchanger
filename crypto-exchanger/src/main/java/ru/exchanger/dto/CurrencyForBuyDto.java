package ru.exchanger.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Builder
@ToString
public class CurrencyForBuyDto {
    private int currencyId;         // SERIAL PRIMARY KEY
    private String code;            // BTC, ETH, RUB
    @Setter
    private String name;            // Полное название валюты
    private BigDecimal amount;      // объем резерва валюты, NUMERIC(32,8)
}
