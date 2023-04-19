package com.kellot.student_management_api.repository;

import com.kellot.student_management_api.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    private EntityManager mockEntityManager;
    private UserRepository userRepo;
    @BeforeEach
    void beforeEach(){
        mockEntityManager = Mockito.mock(EntityManager.class);
        userRepo = new UserRepository(mockEntityManager);
    }

    @Test
    void findById_showReturnRequestedUser() {
        // Arrange
        User user = new User(1L,"John",20,"john@gmail.com");
        Mockito.when(mockEntityManager.find(any(), any(Long.class)))
                .thenReturn(user);

        // Act
        User result = userRepo.findById(1).get();

        // Assert
        assertEquals(user.getUsername(),result.getUsername());
        assertEquals(user.getAge(),result.getAge());
        assertEquals(user.getEmail(),result.getEmail());
    }

    @Test
    void findAll_shouldReturnAllUsersInPersistenceContext() {
        List<User> userList = new ArrayList<>();
        Collections.addAll(userList,
                new User(1L,"John",20,"john1@gmail.com"),
                new User(2L,"Jane",25,"jane@gmail.com"),
                new User(3L,"Bob",30,"bob@gmail.com")
        );
        TypedQuery<User> mockQuery = mock(TypedQuery.class);
        when(mockEntityManager.createNamedQuery(eq("find_all_users"),eq(User.class))).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(userList);

        List<User> result = userRepo.findAll();

        int index =result.size();
        assertEquals(userList.size(),result.size());
        for (int i = 0; i < index; i++) {
            assertEquals(userList.get(i).getUsername(),result.get(i).getUsername());
            assertEquals(userList.get(i).getAge(),result.get(i).getAge());
            assertEquals(userList.get(i).getEmail(), result.get(i).getEmail());
        }

    }

    @Test
    void save_returnUserShouldEqualToTheOriginalUser() {
        User user = new User(1L,"John",20,"john@gmail.com");
        Mockito.when(mockEntityManager.merge(any())).thenReturn(user);

        User result = userRepo.save(user);

        assertEquals(user.getUsername(),result.getUsername());
        assertEquals(user.getAge(),result.getAge());
        assertEquals(user.getEmail(),result.getEmail());
    }

    @Test
    void update() {
        User user = new User(1L,"John",20,"john@gmail.com");
        Mockito.when(mockEntityManager.merge(any())).thenReturn(user);

        User result = userRepo.update(user);

        assertEquals(user.getUsername(),result.getUsername());
        assertEquals(user.getAge(),result.getAge());
        assertEquals(user.getEmail(),result.getEmail());
    }

    @Test
    void deleteById() {
        User user = new User(1L,"John",20,"john@gmail.com");
        Mockito.when(mockEntityManager.find(any(),any(Long.class))).thenReturn(user);

        userRepo.deleteById(1);

        Mockito.verify(mockEntityManager,times(1)).remove(user);
    }

    @Test
    void deleteAll() {
        List<User> userList = new ArrayList<>();
        Collections.addAll(userList,
                new User(1L,"John",20,"john1@gmail.com"),
                new User(2L,"Jane",25,"jane@gmail.com"),
                new User(3L,"Bob",30,"bob@gmail.com")
        );

        TypedQuery<User> mockQuery = mock(TypedQuery.class);
        when(mockEntityManager.createNamedQuery(eq("find_all_users"),eq(User.class))).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(userList);

        userRepo.deleteAll();

        verify(mockQuery,times(1)).getResultList();
        verify(mockEntityManager,times(userList.size())).remove(any(User.class));
    }
}