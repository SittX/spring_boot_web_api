package com.kellot.student_management_api.repository;

import com.kellot.student_management_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
