package com.example.securitypractice.service;

import com.example.securitypractice.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface UserService {
    public Optional<User> getById(Long id);

    public List<User> getAll();

    public User save(User user);

    public User updateUser(User user);
}
