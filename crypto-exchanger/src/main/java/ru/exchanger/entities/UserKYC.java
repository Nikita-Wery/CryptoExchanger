package ru.exchanger.entities;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class UserKYC {
    private UUID userId;         // user_id UUID (FK → Users.id)
    private String personalData; // JSONB (строка в виде JSON)
    private String documents;    // JSONB (строка в виде JSON)
    private String firstName;    // VARCHAR(50)
    private String secondName;   // VARCHAR(50)
}
