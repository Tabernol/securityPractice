package com.example.securitypractice.service;

import com.example.securitypractice.database.entity.PasswordResetToken;
import com.example.securitypractice.database.repository.PasswordResetTokenRepo;
import com.example.securitypractice.database.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PasswordResetTokenServiceTest {
    @InjectMocks
    private PasswordResetTokenService passwordResetTokenService;

    @Mock
    private PasswordResetTokenRepo passwordResetTokenRepo;

    @Mock
    private UserRepo userRepo;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        passwordResetTokenService = new PasswordResetTokenService(passwordResetTokenRepo, userRepo);
    }

    @Test
    void ifPasswordAndRepeatPasswordTheSameTest() {
        String password = "myPassword";
        String repeatPassword = "myPassword";
        boolean result = passwordResetTokenService.ifPasswordAndRepeatPasswordTheSame(password, repeatPassword);
        assertTrue(result, "Passwords should be considered the same");
    }

    @Test
    void testIfPasswordAndRepeatPasswordNotTheSame() {
        String password = "password123";
        String repeatPassword = "differentPassword";
        boolean result = passwordResetTokenService.ifPasswordAndRepeatPasswordTheSame(password, repeatPassword);
        assertFalse(result, "Passwords should not be considered the same");
    }

}