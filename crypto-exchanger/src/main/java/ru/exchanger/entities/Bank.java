package ru.exchanger.entities;

import lombok.*;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Bank {
    private Long bankId;
    private String name;
    private String numberFormat; // формат номера карты банка
}
