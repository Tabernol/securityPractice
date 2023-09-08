package com.example.securitypractice.dto;

import lombok.Data;

@Data
public class ResetPasswordDto {
    private String token;
    private String password;
    private String repeatPassword;
}
