package com.example.securitypractice.controller;

import com.example.securitypractice.entity.User;
import com.example.securitypractice.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users/{id}")
    public User getById(@PathVariable(value = "id") Long id){
        System.out.println("=============================");
//        User user = new User();
//        user.setLogin("log");
//        user.setName("Ivan");
        User user = userService.getById(id).orElseThrow();
        return user;
    }
}
