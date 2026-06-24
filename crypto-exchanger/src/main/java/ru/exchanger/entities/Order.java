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
public class Order {
    private Long orderId;
    private UUID userId;
    private Integer currencySellId;
    private Integer currencyBuyId;
    private BigDecimal amountSell;
    private BigDecimal amountBuy;
    private String status;
    private OffsetDateTime createdAt;
}
