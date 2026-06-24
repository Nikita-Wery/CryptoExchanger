package ru.exchanger.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Builder
@Setter
public class UserDto {
    private UUID id;
    private String email;
    private String passwordHash;
    private String role; // admin, user, moderator
}
