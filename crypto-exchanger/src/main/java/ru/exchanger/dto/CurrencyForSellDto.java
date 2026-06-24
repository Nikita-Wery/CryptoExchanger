package ru.exchanger.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class CurrencyForSellDto {
    private int currencyId; // SERIAL Primary Key
    private String code;  // BTC, ETH, SOL
    @Setter
    private String effectiveCode;  // в случае если вытянули RUB, USD
    @Setter
    private String name;
    private int bankId; // соеденяется с RUB, USD и образует effective code
    private String bankName;
}
