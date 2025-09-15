package com.example.TaskManager.dto;


import com.example.TaskManager.entity.Role;
import com.example.TaskManager.entity.Task;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;

public class JwtResponse {

    private Long id;
    private String username;
    private String password;

    private HashSet<Role> roles = new HashSet<>();

    private HashSet<Task> tasks = new HashSet<>();
    private String token;
    private String type = "Bearer";

}
