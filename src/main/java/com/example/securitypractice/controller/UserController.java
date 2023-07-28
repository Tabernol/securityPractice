package com.example.securitypractice.controller;

import com.example.securitypractice.dto.UserPostDto;
import com.example.securitypractice.entity.User;
import com.example.securitypractice.mapper.UserMapper;
import com.example.securitypractice.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

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
        model.addAttribute("usersDto", userMapper.mapToDto(userService.getAll()));
        return "user/users";
    }
    @GetMapping("/userEdit/{id}")
    public String editUser(@PathVariable(name = "id") Long id, Model model){
        model.addAttribute("user", userService.getById(id).orElseThrow());
        return "user/userEdit";
    }

    @PostMapping("/registration")
    public String save(UserPostDto userPostDto, Model model) {
        User user = userMapper.mapToEntity(userPostDto);
        User saveUser = userService.save(user);
        model.addAttribute("user", saveUser);
        return "user/user";
    }

    @PostMapping("/users/{id}")
    public String editUser(@PathVariable(value = "id") Long id, UserPostDto userPostDto, Model model) {
        User user = userService.getById(id).orElseThrow();
        user.setName(userPostDto.getName());
        User save = userService.save(user);
        model.addAttribute("user", save);
        return "user/user";
    }

    @PostMapping("/users_delete/{id}")
    public String deleteUser(@PathVariable(value = "id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

}
