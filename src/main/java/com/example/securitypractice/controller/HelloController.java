package com.example.securitypractice.controller;

import com.example.securitypractice.dto.UserPostDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HelloController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/registration")
    public String toRegistration(Model model, @ModelAttribute("user") UserPostDto userPostDto) {
        model.addAttribute("user", userPostDto);
        return "registration";
    }

    @GetMapping("/login")
    public String toLogin() {
        return "login";
    }

//    @GetMapping("/logout")
//    public String logout() {
//        System.out.println("LOG=============");
//        return "home";
//    }
}
