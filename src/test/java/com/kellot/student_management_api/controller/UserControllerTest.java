package com.kellot.student_management_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kellot.student_management_api.model.User;
import com.kellot.student_management_api.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
Steps that are required to test a controller class
 1. Mock the controller dependencies ( e.g: Service class )
 2. Mock the request to the controller using MockMvc
 3. Build the request using MockMvcRequestBuilders
 4. Send the request to the controller
5. Assert the returned result from the controller
*/
@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getUsers_shouldReturnUsersList() throws Exception {
        // Initialized user list
        List<User> users = new ArrayList<>();
        Collections.addAll(users, new User("Kevin", 29, "kevin@gmail.com"), new User("David", 22, "david@gmail.com"), new User("John", 59, "nicole@gmail.com"));
        Mockito.when(userService.getUsers()).thenReturn(users);

        // Build Mock Request
        RequestBuilder request = MockMvcRequestBuilders.get("/api/users")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", is(3)));
    }

    @Test
    void getUserById_shouldReturnUserWithTheRequestedID() throws Exception {
        // Defining the behaviour of the mocked userService
        long USER_ID = 1L;
        User existingUser = new User("Micheal", 22, "micheal@gmail.com");
        when(userService.getUserById(USER_ID)).thenReturn(existingUser);

        // Build Mock Request
        RequestBuilder request = MockMvcRequestBuilders.get("/api/users/1").contentType(MediaType.APPLICATION_JSON);

        // Send request and assert the response
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.username", is(existingUser.getUsername())))
                .andExpect(jsonPath("$.age", is(existingUser.getAge())))
                .andExpect(jsonPath("$.email", is(existingUser.getEmail())))
                .andDo(print());
    }

    @Test
    void updateUser_shouldReturnUpdatedUser() throws Exception {
        User user = new User("K", 21, "k@gmail.com");
        user.setId(1L);
        String userJson = objectMapper.writeValueAsString(user);

        Mockito.when(userService.updateUser(Mockito.anyLong(), Mockito.any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(1));

        RequestBuilder request = MockMvcRequestBuilders.put("/api/users/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.age", is(user.getAge())))
                .andExpect(jsonPath("$.email", is(user.getEmail())));
    }


    @Test
    void deleteUser_shouldReturnAcceptedStatusCode() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete("/api/users/{id}", 1);
        mockMvc.perform(request).andExpect(status().isAccepted()).andDo(print());
    }

    @Test
    void deleteAllUser_shouldReturnOkStatusCode() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete("/api/users");
        mockMvc.perform(request).andExpect(status().isOk()).andDo(print());
    }
}