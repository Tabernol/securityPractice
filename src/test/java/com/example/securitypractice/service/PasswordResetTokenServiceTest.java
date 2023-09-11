package com.example.securitypractice.service;

import com.example.securitypractice.database.entity.PasswordResetToken;
import com.example.securitypractice.database.entity.User;
import com.example.securitypractice.database.repository.PasswordResetTokenRepo;
import com.example.securitypractice.database.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
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

    @Test
    void getByTokenTest() {
        String testToken = "your_test_token";
        PasswordResetToken expectedToken = new PasswordResetToken();
        Mockito.when(passwordResetTokenRepo.findByToken(testToken)).thenReturn(Optional.of(expectedToken));

        Optional<PasswordResetToken> result = passwordResetTokenService.getByToken(testToken);
        Mockito.verify(passwordResetTokenRepo).findByToken(testToken);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(expectedToken, result.get());
    }

    @Test
    void ifTokenValid_WithValidTokenTest() {

        String validToken = "valid_token";
        PasswordResetToken validPasswordResetToken = new PasswordResetToken();
        validPasswordResetToken.setExpiryDate(LocalDateTime.now().plusHours(1));

        Mockito.when(passwordResetTokenRepo.findByToken(validToken)).thenReturn(Optional.of(validPasswordResetToken));

        boolean result = passwordResetTokenService.ifTokenValid(validToken);

        Mockito.verify(passwordResetTokenRepo).findByToken(validToken);

        Assertions.assertTrue(result);
    }

    @Test
    void ifTokenValid_WithExpiredTokenTest() {
        String expiredToken = "expired_token";
        PasswordResetToken expiredPasswordResetToken = new PasswordResetToken();
        expiredPasswordResetToken.setExpiryDate(LocalDateTime.now().minusHours(1));
        Mockito.when(passwordResetTokenRepo.findByToken(expiredToken)).thenReturn(Optional.of(expiredPasswordResetToken));

        boolean result = passwordResetTokenService.ifTokenValid(expiredToken);

        Mockito.verify(passwordResetTokenRepo, Mockito.times(1)).findByToken(expiredToken);
        Assertions.assertFalse(result);
    }

}