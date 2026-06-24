package ru.exchanger.entities;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Currency {
    private int currencyId;         // SERIAL PRIMARY KEY
    private String code;            // BTC, ETH, RUB
    private String name;            // Полное название валюты
    private BigDecimal amount;      // объем резерва валюты, NUMERIC(32,8)
    private String description;     // описание
    private String role;            // for_sale, for_buy, both, frozen
    private String type;            // crypto, fiat
    private boolean isActive;       // активна ли валюта
}
