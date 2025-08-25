package com.example.TaskManager.hendler;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data // <----------- ВО ВСЕХ ГРЕХАХ ЭТОГО МИРА ВИНОВАТА ЭТА АННОТАЦИЯ!!! 'U+269D'
public class ApiError {
    private String message;
    private HttpStatus status;
    private LocalDateTime timestamp;

    public ApiError(String message, HttpStatus status, LocalDateTime timestamp) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
