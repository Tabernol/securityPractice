package com.example.securitypractice.controller;

import com.example.securitypractice.database.entity.Role;
import com.example.securitypractice.database.entity.User;
import com.example.securitypractice.dto.PageResponse;
import com.example.securitypractice.dto.UserFilter;
import com.example.securitypractice.dto.UserGetDto;
import com.example.securitypractice.dto.UserPostDto;
import com.example.securitypractice.mapper.UserMapper;
import com.example.securitypractice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api")
@Slf4j
public class UserRestController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    public UserRestController(UserService userService, UserMapper userMapper, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
    }


    @GetMapping("/users/{id}")
    public UserGetDto getById(@PathVariable(value = "id") Long id) {
        return userMapper.mapToDto(userService.getById(id).orElseThrow());

    }

    @GetMapping("/users")
    public Page<User> getAll(UserFilter userFilter, Pageable pageable) {
        Page<User> page = userService.findAll(userFilter, pageable);
        return page;
    }


    //check this method
    @PatchMapping("/users/{id}")
    public UserGetDto editUser(@PathVariable(name = "id") Long id, @RequestBody UserPostDto userPostDto) {
        User user = userMapper.mapToEntity(userPostDto);
        User updatedUser = userService.update(user);
        log.info(String.valueOf(updatedUser));
        return userMapper.mapToDto(updatedUser);
    }
}
