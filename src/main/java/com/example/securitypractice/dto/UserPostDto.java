package com.example.securitypractice.dto;

import com.example.securitypractice.database.entity.Role;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class UserPostDto {
    @NotBlank
    @Size(min = 3, max = 64)
    private String name;
    @Email
    private String login;
    //custom validation for group
    @NotBlank()
    private String rawPassword;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private Role role;
}
