package com.example.securitypractice.service;

import com.example.securitypractice.dto.UserFilter;
import com.example.securitypractice.database.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.List;
import java.util.Optional;

public interface UserService extends  UserDetailsService {
    Optional<User> getById(Long id);

    List<User> findAll();

    Page<User> findAll(UserFilter userFilter, Pageable pageable);

    User save(User user);

    User save(OidcUser oidcUser);

    User update(User user);

    boolean delete(Long id);

    boolean ifExist(String login);

    Optional<User> getByLogin(String login);

    boolean changePassword(Long userId, String newPassword);


}
