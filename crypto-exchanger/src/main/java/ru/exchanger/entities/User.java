package ru.exchanger.entities;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class User {
    @Builder.Default
    private UUID id = UUID.randomUUID();
    private String email;
    private String passwordHash;
    private String role;
    private OffsetDateTime createdAt;
    private OffsetDateTime lastLogin;
}
