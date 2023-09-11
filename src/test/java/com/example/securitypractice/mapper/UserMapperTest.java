package com.example.securitypractice.mapper;

import com.example.securitypractice.database.entity.Role;
import com.example.securitypractice.database.entity.User;
import com.example.securitypractice.dto.UserGetDto;
import com.example.securitypractice.dto.UserPostDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserMapperTest {
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserMapper userMapper;


    @Test
    void testMapToEntity() {
        UserPostDto userPostDto = new UserPostDto();
        userPostDto.setLogin("testuser");
        userPostDto.setName("Test User");
        userPostDto.setRawPassword("password123");
        userPostDto.setBirthDate(LocalDate.of(1990, 1, 1));


        User user = userMapper.mapToEntity(userPostDto);

        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encodePassword");

        assertEquals(userPostDto.getLogin(), user.getLogin());
        assertEquals(userPostDto.getName(), user.getName());
        assertEquals(Role.USER, user.getRole());
        assertEquals(userPostDto.getBirthDate(), user.getBirthDate());
    }

    @Test
    void mapToDtoTest() {
        UserMapper usermapper = new UserMapper(passwordEncoder);
        User user = new User();
        user.setId(1L);
        user.setLogin("testuser");
        user.setName("Test User");
        user.setRole(Role.USER);
        user.setBirthDate(LocalDate.of(1990, 1, 1));

        UserGetDto userDto = usermapper.mapToDto(user);

        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getLogin(), userDto.getLogin());
        assertEquals(user.getName(), userDto.getName());
        assertEquals(user.getRole().name(), userDto.getRole());
        assertEquals(user.getBirthDate(), userDto.getBirthDate());
    }
}