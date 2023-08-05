package com.example.securitypractice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserPostDto {
    private String name;
    private String login;
    private String password;
    private LocalDate birthDate;
}
