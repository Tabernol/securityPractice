package com.example.securitypractice.service;

import com.example.securitypractice.database.entity.PasswordResetToken;
import com.example.securitypractice.database.repository.PasswordResetTokenRepo;
import com.example.securitypractice.database.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetTokenService {
    private final PasswordResetTokenRepo passwordResetTokenRepo;
    private final UserRepo userRepo;

    public PasswordResetTokenService(PasswordResetTokenRepo passwordResetTokenRepo, UserRepo userRepo) {
        this.passwordResetTokenRepo = passwordResetTokenRepo;
        this.userRepo = userRepo;
    }

    public PasswordResetToken save(String email) {
        // throw an exception if the user is not found
        Long userId = userRepo.findByLogin(email).orElseThrow().getId();
        UUID uuid = UUID.randomUUID();
        LocalDateTime expireDate = LocalDateTime.now().plusSeconds(300L);

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUserId(userId);
        passwordResetToken.setExpiryDate(expireDate);
        passwordResetToken.setToken(uuid.toString());


        return passwordResetTokenRepo.save(passwordResetToken);
    }

    public boolean ifTokenValid(String token) {
        if (passwordResetTokenRepo.findByToken(token).isPresent()) {
            return passwordResetTokenRepo
                    .findByToken(token)
                    .get()
                    .getExpiryDate()
                    .isAfter(LocalDateTime.now());
        }
        return false;
    }

    public boolean ifPasswordAndRepeatPasswordTheSame(String password, String repeatPassword) {
        return password.equals(repeatPassword);
    }

    public Optional<PasswordResetToken> getByToken(String token) {
        return passwordResetTokenRepo.findByToken(token);
    }
}
