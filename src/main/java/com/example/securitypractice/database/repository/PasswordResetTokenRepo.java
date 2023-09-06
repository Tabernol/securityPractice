package com.example.securitypractice.database.repository;

import com.example.securitypractice.database.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepo extends
        JpaRepository<PasswordResetToken, Long> {
}
