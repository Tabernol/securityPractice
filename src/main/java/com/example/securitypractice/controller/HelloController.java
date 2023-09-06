package com.example.securitypractice.controller;

import com.example.securitypractice.dto.UserPostDto;
import com.example.securitypractice.service.EmailService;
import com.example.securitypractice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class HelloController {

    private final EmailService emailService;
    private final UserService userService;

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

    @GetMapping("/login/forgot")
    public String toRecoverPassword() {
        return "forgot_password";
    }

    @PostMapping("/login/forgot")
    public String recoverPassword(@ModelAttribute("username") String email) {
        System.out.println("email for recover password " + email);


        //if email exist
        if (userService.ifExist(email)) {
            emailService.sendEmail(email, "testSubject", "testMessage");
        } else {
            throw new IllegalArgumentException("User with login " + email + " not found");
        }


        //letter with redirect to change password
        return "reset_password";
    }

//    @GetMapping("/logout")
//    public String logout() {
//        System.out.println("LOG=============");
//        return "home";
//    }
}
