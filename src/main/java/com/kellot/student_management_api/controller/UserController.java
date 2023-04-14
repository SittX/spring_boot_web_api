package com.kellot.student_management_api.controller;


import com.kellot.student_management_api.model.User;
import com.kellot.student_management_api.service.UserService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

// TODO Write Unit tests for the controller

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id){
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
        var result = userService.getUsers();
        System.out.println(Arrays.toString(userService.getUsers().toArray()));
        return new ResponseEntity<>(userService.getUsers(),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user){
       return new ResponseEntity<>(userService.saveUser(user),HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable long id){
        User updatedUser = userService.updateUser(id,user);
        if(updatedUser == null){
            throw new ObjectNotFoundException("Object cannot be updated",User.class);
        }
        return new ResponseEntity<>(updatedUser,HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<List<User>> updateAllUser(@RequestBody User user){
        return new ResponseEntity<>(userService.updateAllUser(user),HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteAllUser(){
        userService.deleteAllUser();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public UserService getUserService(){
        return this.userService;
    }
}
