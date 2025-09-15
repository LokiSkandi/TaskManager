package com.example.TaskManager.repository;

import com.example.TaskManager.entity.User;
import io.swagger.v3.oas.models.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername (String username);
    Boolean existsByUsername (String username);
}
