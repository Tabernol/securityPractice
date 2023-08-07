package com.example.securitypractice.dto;

import lombok.*;

import java.time.LocalDate;

@Data
public class UserGetDto {
    private Long id;
    private String login;
    private String name;
    private LocalDate birthDate;
    private String role;

}
