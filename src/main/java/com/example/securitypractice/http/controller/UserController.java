package com.example.securitypractice.http.controller;

import com.example.securitypractice.database.entity.Role;
import com.example.securitypractice.dto.PageResponse;
import com.example.securitypractice.dto.UserFilter;
import com.example.securitypractice.dto.UserPostDto;
import com.example.securitypractice.database.entity.User;
import com.example.securitypractice.mapper.UserMapper;
import com.example.securitypractice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
//@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, UserMapper userMapper, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
    }


    @GetMapping("/users/{id}")
    public String getById(@PathVariable(value = "id") Long id, Model model) {
        User user = userService.getById(id).orElseThrow();
        model.addAttribute("user", user);
        return "user/user";
    }

    @GetMapping("/users/me/{login}")
    public String getByLogin(@PathVariable("login") String login, Model model) {
        log.info("login ==================== " + login);
        User user = userService.getByLogin(login).orElseThrow();
        model.addAttribute("user", user);
        return "user/user";
    }

    @GetMapping("/users")
    public String getAll(Model model, UserFilter userFilter, Pageable pageable) {

//        Recyclable paper = new Paper();
//        Recyclable paper1 = new Paper(12.5);
//        Recyclable paper2 = new Paper(78);
//        log.info(paper.toString());
//        log.info(paper1.toString());
//        log.info(paper2.toString());

        Page<User> page = userService.findAll(userFilter, pageable);

        model.addAttribute("usersDto", PageResponse.of(page));
        model.addAttribute("filter", userFilter);
        return "user/users";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("user", userService.getById(id).orElseThrow());
        model.addAttribute("roles", Role.values());
        return "user/userEdit";
    }

    @PostMapping("/registration")
    public String save(@ModelAttribute @Validated UserPostDto userPostDto,
                       BindingResult bindingResult,
                       Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", userPostDto);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/registration";
        }

        User user = userMapper.mapToEntity(userPostDto);
        User saveUser = userService.save(user);
        log.info(user.toString());
        model.addAttribute("user", saveUser);
        authenticateUser(userPostDto.getLogin(), userPostDto.getRawPassword());
        return "redirect:/users";
    }

    @PostMapping("/users/{id}")
    public String editUser(@PathVariable(value = "id") Long id, UserPostDto userPostDto, Model model) {
        User user = userService.getById(id).orElseThrow();

// Need mapper for update
        user.setName(userPostDto.getName());
        user.setBirthDate(userPostDto.getBirthDate());
        user.setRole(userPostDto.getRole());

        User save = userService.save(user);
        model.addAttribute("user", save);
        return "user/user";
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteUser(@PathVariable(value = "id") Long id) {
        userService.delete(id);
        return "redirect:/users";
    }

    private void authenticateUser(String login, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("===================================");
        log.info("Credentials = " + authentication.getCredentials().toString());
    }

}
