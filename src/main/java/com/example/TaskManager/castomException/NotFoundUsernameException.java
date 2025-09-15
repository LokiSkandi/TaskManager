package com.example.TaskManager.castomException;

public class NotFoundUsernameException extends Exception{
    public NotFoundUsernameException(String message) {
        super(message);
    }
}
