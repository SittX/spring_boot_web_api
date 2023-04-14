package com.kellot.student_management_api.service.impl;

import com.kellot.student_management_api.model.User;
import com.kellot.student_management_api.repository.UserRepository;
import com.kellot.student_management_api.service.UserService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepo;

    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserById(long id) {
        return userRepo.findById(id).orElseThrow(()-> new ObjectNotFoundException("User with the given id is not found.",User.class));
    }

    @Override
    public User saveUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public User updateUser(long id, User user) {
        User existingUser = userRepo.findById(id).orElseThrow(() -> new ObjectNotFoundException("User with the given is not found.",User.class));
        existingUser.setUsername(user.getUsername());
        existingUser.setAge(user.getAge());
        existingUser.setEmail(user.getEmail());
        return userRepo.save(existingUser);
    }

    @Override
    public void deleteUser(long id) {
        userRepo.deleteById(id);
    }

    @Override
    public void deleteAllUser() {
       userRepo.deleteAll();
    }

    @Override
    public List<User> updateAllUser(User user) {
       List<User> updatedUserList = userRepo.findAll().stream().map((u)-> {
           u.setUsername(user.getUsername());
           u.setAge(user.getAge());
           u.setEmail(user.getEmail());
           return userRepo.save(u);
        }).toList();
       return updatedUserList;
    }
}
