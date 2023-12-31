package com.example.securitypractice.mapper;

import com.example.securitypractice.dto.UserGetDto;
import com.example.securitypractice.dto.UserPostDto;
import com.example.securitypractice.database.entity.Role;
import com.example.securitypractice.database.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public UserGetDto mapToDto(User user){
        UserGetDto userDto = new UserGetDto();
        userDto.setId(user.getId());
        userDto.setLogin(user.getLogin());
        userDto.setName(user.getName());
        userDto.setRole(user.getRole().name());
        userDto.setBirthDate(user.getBirthDate());
        return userDto;
    }

    public List<UserGetDto> mapToDto(List<User> userList){
        List<UserGetDto> userDtoList = new ArrayList<>();
        for(User user: userList){
            UserGetDto userDto = mapToDto(user);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }


    public User mapToEntity(UserPostDto userPostDto){
        User user = new User();
        user.setLogin(userPostDto.getLogin());
        user.setName(userPostDto.getName());
        user.setRole(Role.USER);
        user.setBirthDate(userPostDto.getBirthDate());
        user.setPassword(passwordEncoder.encode(userPostDto.getRawPassword()));
        return user;
    }
}
