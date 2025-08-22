package com.example.TaskManager.hendler;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private String messege;
    private HttpStatus status;
    private LocalDateTime timestamp;

    public ApiError(String messege, HttpStatus status, LocalDateTime timestamp) {
        this.messege = messege;
        this.status = status;
        this.timestamp = timestamp;
    }
}
