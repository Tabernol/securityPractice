package com.example.securitypractice.service;

import com.example.securitypractice.database.entity.Role;
import com.example.securitypractice.database.entity.User;
import com.example.securitypractice.database.repository.UserRepo;
import com.example.securitypractice.dto.UserFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static final Long USER_ID = 21L;

    @Mock
    private UserRepo userRepo;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepo);
    }

    @Test
    void getByIdTest() {
        User user = new User();
        when(userRepo.findById(USER_ID)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getById(USER_ID);

        assertEquals(Optional.of(user), result);
    }

    @Test
    void saveUserTest() {
        User userToSave = new User();
        userToSave.setName("testuser");
        userToSave.setLogin("test@example.com");

        when(userRepo.save(any(User.class))).thenReturn(userToSave);

        User savedUser = userService.save(userToSave);

        verify(userRepo, times(1)).save(userToSave);

        assertEquals("testuser", savedUser.getName());
        assertEquals("test@example.com", savedUser.getLogin());
    }

    @Test
    void findAllUsersTest() {
        List<User> userList = new ArrayList<>();
        userList.add(User.builder().name("user1").login("user1@ua").build());
        userList.add(User.builder().name("user2").login("user2@ua").build());
        userList.add(User.builder().name("user3").login("user3@ua").build());

        when(userRepo.findAll()).thenReturn(userList);

        List<User> retrievedUsers = userService.findAll();

        verify(userRepo, times(1)).findAll();

        assertEquals(userList.size(), retrievedUsers.size());

        for (int i = 0; i < userList.size(); i++) {
            assertEquals(userList.get(i), retrievedUsers.get(i));
        }
    }

    @Test
    void updateUserTest() {
        // Create a User object to update
        User userToUpdate = new User();
        userToUpdate.setId(1L);
        userToUpdate.setName("updatedUser");
        userToUpdate.setLogin("updated@example.com");

        when(userRepo.saveAndFlush(any(User.class))).thenReturn(userToUpdate);

        User updatedUser = userService.update(userToUpdate);
        verify(userRepo, times(1)).saveAndFlush(userToUpdate);
        assertEquals(1L, updatedUser.getId());
        assertEquals("updatedUser", updatedUser.getName());
        assertEquals("updated@example.com", updatedUser.getLogin());
    }

    @Test
    void deleteUserTest() {
        Long userIdToDelete = 1L;
        userService.delete(userIdToDelete);
        verify(userRepo, times(1)).deleteById(userIdToDelete);
    }

    @Test
    void ifExistWithExistingLoginTest() {
        String existingLogin = "existingUser";

        when(userRepo.findByLogin(existingLogin)).thenReturn(Optional.of(new User()));
        boolean exists = userService.ifExist(existingLogin);

        verify(userRepo, times(1)).findByLogin(existingLogin);
        assertTrue(exists);
    }

    @Test
    void ifExistWithNonExistingLoginTest() {
        String nonExistingLogin = "nonExistingUser";
        when(userRepo.findByLogin(nonExistingLogin)).thenReturn(Optional.empty());

        boolean exists = userService.ifExist(nonExistingLogin);

        verify(userRepo, times(1)).findByLogin(nonExistingLogin);

        assertFalse(exists);
    }

    @Test
    void getByLoginWithExistingLoginTest() {
        String existingLogin = "existingUser";
        User user = new User();
        user.setLogin(existingLogin);
        when(userRepo.findByLogin(existingLogin)).thenReturn(Optional.of(user));
        Optional<User> retrievedUser = userService.getByLogin(existingLogin);
        verify(userRepo, times(1)).findByLogin(existingLogin);
        assertTrue(retrievedUser.isPresent());
        assertEquals(existingLogin, retrievedUser.get().getLogin());
    }

    @Test
    void getByLoginWithNonExistingLoginTest() {
        String nonExistingLogin = "nonExistingUser";
        when(userRepo.findByLogin(nonExistingLogin)).thenReturn(Optional.empty());
        Optional<User> retrievedUser = userService.getByLogin(nonExistingLogin);
        verify(userRepo, times(1)).findByLogin(nonExistingLogin);
        assertFalse(retrievedUser.isPresent());
    }

    @Test
    void loadUserByUsernameWithExistingUserTest() {

        String existingUsername = "existingUser";

        User user = new User();
        user.setLogin(existingUsername);
        user.setPassword("password");
        user.setRole(Role.USER);


        when(userRepo.findByLogin(existingUsername)).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername(existingUsername);


        verify(userRepo, times(1)).findByLogin(existingUsername);


        assertTrue(userDetails instanceof org.springframework.security.core.userdetails.User);
        org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User) userDetails;
        assertEquals(existingUsername, springUser.getUsername());
        assertEquals("password", springUser.getPassword());
        //    assertTrue(springUser.getAuthorities().contains(Role.USER.getAuthority()));
    }

    @Test
    void loadUserByUsernameWithNonExistingUserTest() {
        String nonExistingUsername = "nonExistingUser";
        when(userRepo.findByLogin(nonExistingUsername)).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(nonExistingUsername));
        verify(userRepo, times(1)).findByLogin(nonExistingUsername);
    }

    @Test
    void changePasswordSuccessTest() {
        Long userId = 1L;
        String newPassword = "newPassword";
        when(userRepo.updatePassword(userId, newPassword)).thenReturn(1);
        boolean changePasswordResult = userService.changePassword(userId, newPassword);
        verify(userRepo, times(1)).updatePassword(userId, newPassword);
        assertTrue(changePasswordResult);
    }

    @Test
  void changePasswordFailureTest() {
        Long userId = 1L;
        String newPassword = "newPassword";
        when(userRepo.updatePassword(userId, newPassword)).thenReturn(0);
        boolean changePasswordResult = userService.changePassword(userId, newPassword);
        verify(userRepo, times(1)).updatePassword(userId, newPassword);
        assertFalse(changePasswordResult);
    }
}