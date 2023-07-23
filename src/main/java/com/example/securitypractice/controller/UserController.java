package com.example.securitypractice.controller;

import com.example.securitypractice.dto.UserPostDto;
import com.example.securitypractice.entity.User;
import com.example.securitypractice.mapper.UserMapper;
import com.example.securitypractice.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }


    @GetMapping("/users/{id}")
    public String getById(@PathVariable(value = "id") Long id, Model model) {
        User user = userService.getById(id).orElseThrow();
        model.addAttribute("user", user);
        return "user/user";
    }

    @GetMapping("/users")
    public String getAll(Model model) {
        for (User user : userService.getAll()) {
            System.out.println(user);
        }
        model.addAttribute("usersDto", userMapper.mapToDto(userService.getAll()));
        return "user/users";
    }

    @PostMapping("/registration")
    public String save(UserPostDto userPostDto) {
        User user = userMapper.mapToEntity(userPostDto);
        userService.save(user);
        return "user/user";
    }

    @PostMapping("/editUser")
    public String editUser(@PathVariable(value = "id") Long id, UserPostDto userPostDto){
        User user = userMapper.mapToEntity(userPostDto);
        userService.save(user);
        return "user/user";

    }



}
