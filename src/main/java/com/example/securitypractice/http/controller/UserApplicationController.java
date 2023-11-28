package com.example.securitypractice.http.controller;

import com.example.securitypractice.database.entity.recyclable.Recyclable;
import com.example.securitypractice.database.entity.recyclable.TypeGarbage;
import com.example.securitypractice.service.RecyclableService;
import com.example.securitypractice.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserApplicationController {

    private final UserService userService;
    private final RecyclableService recyclableService;

    public UserApplicationController(UserService userService, RecyclableService recyclableService) {
        this.userService = userService;
        this.recyclableService = recyclableService;
    }

    @GetMapping("/toCreateApplication")
    public String goToCreateApplication(Model model) {
         model.addAttribute("types", TypeGarbage.values());
        return "user/userApplication";
    }
}
