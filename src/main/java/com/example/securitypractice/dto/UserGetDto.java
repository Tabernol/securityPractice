package com.example.securitypractice.dto;

import com.example.securitypractice.entity.Role;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserGetDto {
    private Long id;
    private String login;
    private String name;
    private LocalDate birthDate;
    private String role;

}
