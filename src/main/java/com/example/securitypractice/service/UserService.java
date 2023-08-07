package com.example.securitypractice.service;

import com.example.securitypractice.database.entity.User;
import com.example.securitypractice.dto.UserFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface UserService {
    Optional<User> getById(Long id);

    List<User> findAll();

    Page<User> findAll(UserFilter userFilter, Pageable pageable);

    User save(User user);

    User update(User user);

    void delete(Long id);


}
