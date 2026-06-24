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
public class BankCurrency {
    private Integer bankId;         // bank_id INT (FK)
    private Integer currencyId;     // currency_id INT (FK)
    private BigDecimal feePercent;  // fee_percent NUMERIC(5,2)
    private BigDecimal limitMin;    // limit_min NUMERIC(20,8)
    private BigDecimal limitMax;    // limit_max NUMERIC(20,8)
}
