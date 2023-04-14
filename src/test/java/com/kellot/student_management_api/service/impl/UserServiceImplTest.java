package com.kellot.student_management_api.service.impl;

import com.kellot.student_management_api.model.User;
import com.kellot.student_management_api.repository.UserRepository;
import com.kellot.student_management_api.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceImplTest {
    private static UserRepository userRepo;

    @BeforeAll
    static void beforeAll() {
        userRepo = mock(UserRepository.class);
    }

    @Test
    void getUsers_shouldReturnUsersList() {
        List<User> userList = List.of(
                new User("kevin", 29, "kevin@gmail.com"),
                new User("David", 24, "david@gmail.com"));

        when(userRepo.findAll()).thenReturn(userList);

        UserService userService = new UserServiceImpl(userRepo);
        List<User> users = userService.getUsers();

        assertEquals(userList, users);
    }

    @Test
    void updateUser() {
        long existingUserID = 1L;
        User existingUser = new User("Kevin", 29, "kevin@gmail.com");
        User newUser = new User("Updated", 31, "updated@example.com");

        when(userRepo.findById(existingUserID)).thenReturn(Optional.of(existingUser));
        when(userRepo.save(existingUser)).thenReturn(newUser);

        UserService userService = new UserServiceImpl(userRepo);
        User result = userService.updateUser(1L, newUser);
        assertEquals(newUser.getUsername(), result.getUsername());
        assertEquals(newUser.getEmail(), result.getEmail());
        assertEquals(newUser.getAge(), result.getAge());
    }

    @Test
    void updateAllUser() {
        User newUser = new User("Updated", 99, "updated@example.com");

        List<User> existingUsersList = List.of(new User("Kevin", 29, "kevin@gmail.com"), new User("Kevin", 29, "kevin@gmail.com"), new User("Kevin", 29, "kevin@gmail.com"));
        when(userRepo.findAll()).thenReturn(existingUsersList);
        when(userRepo.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserService userService = new UserServiceImpl(userRepo);
        List<User> result = userService.updateAllUser(newUser);

        for (User updatedUser : result) {
            assertEquals(newUser.getUsername(), updatedUser.getUsername());
            assertEquals(newUser.getEmail(), updatedUser.getEmail());
            assertEquals(newUser.getAge(), updatedUser.getAge());
        }
    }
}