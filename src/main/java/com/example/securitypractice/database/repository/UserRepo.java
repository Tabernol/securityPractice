package com.example.securitypractice.database.repository;

import com.example.securitypractice.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepo extends
        JpaRepository<User, Long>,
        QuerydslPredicateExecutor<User> {
    Optional<User> findByLogin(String username);

    @Modifying
    @Query("UPDATE User u SET u.password = :newPassword WHERE u.id = :userId")
    int updatePassword(@Param("userId") Long userId, @Param("newPassword") String newPassword);
}
