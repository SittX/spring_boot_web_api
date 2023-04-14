package com.kellot.student_management_api.service;

import com.kellot.student_management_api.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User getUserById(long id);
    User saveUser(User user);
    User updateUser(long id, User user);
    void deleteUser(long id);
    void deleteAllUser();
    List<User> updateAllUser(User user);
}
