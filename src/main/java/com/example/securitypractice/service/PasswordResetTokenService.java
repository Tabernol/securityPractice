package com.example.securitypractice.service;

import com.example.securitypractice.database.entity.PasswordResetToken;
import com.example.securitypractice.database.repository.PasswordResetTokenRepo;
import com.example.securitypractice.database.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        passwordResetToken.setExpiry_date(expireDate);
        passwordResetToken.setToken(uuid.toString());


        return passwordResetTokenRepo.save(passwordResetToken);
    }
}
