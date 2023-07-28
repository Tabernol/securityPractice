package com.example.securitypractice.service;

import com.example.securitypractice.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getById(Long id);

    List<User> getAll();

    User save(User user);

    User updateUser(User user);
    void deleteUser(Long id);


}
