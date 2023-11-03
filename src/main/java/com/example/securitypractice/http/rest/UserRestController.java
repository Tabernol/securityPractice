package com.example.securitypractice.http.rest;

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
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;

@RestController()
@RequestMapping("/api/v1")
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
    public PageResponse<User> getAll(UserFilter userFilter, Pageable pageable) {
        Page<User> page = userService.findAll(userFilter, pageable);
        return PageResponse.of(page);
    }


    //check this method
    @PatchMapping("/users/{id}")
    public UserGetDto editUser(@PathVariable(name = "id") Long id, @RequestBody UserPostDto userPostDto) {
        User user = userMapper.mapToEntity(userPostDto);
        User updatedUser = userService.update(user);
        log.info(String.valueOf(updatedUser));
        return userMapper.mapToDto(updatedUser);
    }

    @PostMapping("/users")
    public UserGetDto save(@Validated @RequestBody UserPostDto userPostDto) {
        User user = userMapper.mapToEntity(userPostDto);
        User saveUser = userService.save(user);
        log.info("Rest Controller save User = " + saveUser.toString());
        return userMapper.mapToDto(saveUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        if (!userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
