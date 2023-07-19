package com.example.securitypractice.service;

import com.example.securitypractice.dao.UserRepo;
import com.example.securitypractice.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public Optional<User> getById(Long id){
        return userRepo.findById(id);
    }
}
