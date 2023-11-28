package com.example.securitypractice.service;

import com.example.securitypractice.database.entity.QUser;
import com.example.securitypractice.database.entity.Role;
import com.example.securitypractice.database.entity.User;
import com.example.securitypractice.dto.UserFilter;
import com.example.securitypractice.database.querydsl.QPredicates;
import com.example.securitypractice.database.repository.UserRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        for (User user : userRepo.findAll()) {
            userList.add(user);
        }
        return userList;
    }

    @Override
    public Page<User> findAll(UserFilter userFilter, Pageable pageable) {
        var predicate = QPredicates.builder()
                .add(userFilter.name(), QUser.user.name::containsIgnoreCase)
                .add(userFilter.login(), QUser.user.login::containsIgnoreCase)
                .build();
        return userRepo.findAll(predicate, pageable);
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepo.save(user);
    }

    @Override
    @Transactional
    public User save(OidcUser oidcUser) {
        Map<String, Object> claims = oidcUser.getClaims();
        User user = new User();
        user.setName(claims.get("name").toString());
        user.setLogin(claims.get("email").toString());
        //TODO change set password
        user.setPassword(claims.get("nonce").toString());
        user.setRole(Role.USER);
        return userRepo.save(user);
    }

    @Override
    @Transactional
    public User update(User user) {
        return userRepo.saveAndFlush(user);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        return userRepo.findById(id)
                .map(entity -> {
                    userRepo.delete(entity);
                    userRepo.flush();
                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean ifExist(String login) {
        return userRepo.findByLogin(login).isPresent();
    }

    @Override
    public Optional<User> getByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByLogin(username).map(user ->
                        new org.springframework.security.core.userdetails.User(
                                user.getLogin(),
                                user.getPassword(),
                                Collections.singleton(user.getRole())))
                .orElseThrow(()
                        -> new UsernameNotFoundException("Failed to retrieve user: " + username));
    }

    @Transactional
    public boolean changePassword(Long userId, String newPassword) {
        return userRepo.updatePassword(userId, newPassword) == 1;
    }


}
