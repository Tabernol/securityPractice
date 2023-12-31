package com.example.securitypractice.http.controller;

import com.example.securitypractice.dto.ResetPasswordDto;
import com.example.securitypractice.dto.UserPostDto;
import com.example.securitypractice.service.EmailService;
import com.example.securitypractice.service.UserService;
import com.example.securitypractice.database.entity.PasswordResetToken;
import com.example.securitypractice.service.PasswordResetTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final EmailService emailService;
    private final UserService userService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final PasswordEncoder passwordEncoder;

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
    public String recoverPassword() {
        return "forgot_password";
    }

    @PostMapping("/login/forgot")
    public String recoverPassword(@ModelAttribute("username") String email) {
        if (userService.ifExist(email)) {
            PasswordResetToken save = passwordResetTokenService.save(email);

            String resetUrl = "http://localhost:8080/login/reset-password?token=" + save.getToken();
            emailService.sendEmail(email, "Reset Your Password",
                    "To reset your password, click the link below:\n" + resetUrl);
        } else {
            throw new IllegalArgumentException("User with login " + email + " not found");
        }
        return "home";
    }

    @GetMapping("/login/reset-password")
    public String toResetPassword(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "reset_password";
    }

    @PostMapping("/login/reset")
    public String resetPassword(ResetPasswordDto resetPasswordDto) {
        boolean ifTokenValid = passwordResetTokenService.ifTokenValid(resetPasswordDto.getToken());

        boolean ifPasswordTheSame = passwordResetTokenService.ifPasswordAndRepeatPasswordTheSame(
                resetPasswordDto.getPassword(), resetPasswordDto.getRepeat());

        if(!ifPasswordTheSame){
            throw new IllegalArgumentException("passwords are not the same");
        } else if (!ifTokenValid) {
            throw new RuntimeException("time is over for reset password, try again");
        } else {
            Long userId = passwordResetTokenService.getByToken(resetPasswordDto.getToken())
                    .get().getUserId();
            String newPassword = resetPasswordDto.getPassword();
            userService.changePassword(userId, passwordEncoder.encode(newPassword));
            log.info("password has changed");
            return "/home";
        }
    }
}
