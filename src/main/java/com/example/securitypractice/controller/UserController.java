package com.example.securitypractice.controller;

import com.example.securitypractice.dto.UserPostDto;
import com.example.securitypractice.entity.User;
import com.example.securitypractice.mapper.UserMapper;
import com.example.securitypractice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
//@RequestMapping("/users")
@Slf4j
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

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("user", userService.getById(id).orElseThrow());
        return "user/userEdit";
    }

    @PostMapping("/registration")
    public String save(UserPostDto userPostDto, Model model, RedirectAttributes redirectAttributes) {
//        redirectAttributes.addFlashAttribute("user", userPostDto);
//        if (true) {
//            return "redirect:/registration";
//        }
//        For validation
        User user = userMapper.mapToEntity(userPostDto);
        User saveUser = userService.save(user);
        log.info(user.toString());
        model.addAttribute("user", saveUser);
        return "user/user";
    }

    @PostMapping("/users/{id}")
    public String editUser(@PathVariable(value = "id") Long id, UserPostDto userPostDto, Model model) {
        User user = userService.getById(id).orElseThrow();



        user.setName(userPostDto.getName());
        user.setBirthDate(userPostDto.getBirthDate());

        User save = userService.save(user);
        model.addAttribute("user", save);
        return "user/user";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

}
