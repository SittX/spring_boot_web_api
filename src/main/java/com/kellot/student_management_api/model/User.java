package com.kellot.student_management_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
@NamedQuery(name="find_all_users",query = "SELECT u FROM User u")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public User() {
    }

    public User(String username, int age, String email) {
        this.username = username;
        this.age = age;
        this.email = email;
    }

    public User(Long id, String username, int age, String email) {
        this.id = id;
        this.username = username;
        this.age = age;
        this.email = email;
    }

    @Column(name = "username",length = 255, nullable = false,unique = true)
    private String username;

    @Column(name = "age")
    private int age;

    @Column(name = "email",unique = true)
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}
