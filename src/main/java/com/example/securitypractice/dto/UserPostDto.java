package com.example.securitypractice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserPostDto {
    private String name;
    private String login;
    private String password;
    private LocalDateTime birthDate;
}
