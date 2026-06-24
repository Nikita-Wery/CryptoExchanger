package ru.exchanger.entities;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Wallet {
    private long walletId;           // SERIAL PRIMARY KEY
    private UUID userId;             // UUID, внешний ключ на Users(id)
    private int currencyId;          // INT, внешний ключ на Currencies(currency_id)
    private String label;            // label, default 'User wallet'
    private String walletNumber;     // Номер криптокошелька или банковской карты
    private BigDecimal balance;      // NUMERIC(32,8)
    private OffsetDateTime createdAt; // TIMESTAMPT
}
