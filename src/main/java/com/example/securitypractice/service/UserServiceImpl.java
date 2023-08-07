package com.example.securitypractice.service;

import com.example.securitypractice.database.entity.QUser;
import com.example.securitypractice.database.querydsl.QPredicates;
import com.example.securitypractice.database.repository.UserRepo;
import com.example.securitypractice.database.entity.User;
import com.example.securitypractice.dto.UserFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

       return userRepo.findAll(predicate,pageable);
    }

    @Override
    public User save(User user) {
        return userRepo.save(user);
    }

    @Override
    public User update(User user) {
        return userRepo.saveAndFlush(user);
    }

    @Override
    public void delete(Long id) {
        userRepo.deleteById(id);
    }
}
