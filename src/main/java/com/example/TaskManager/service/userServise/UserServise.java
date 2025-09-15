package com.example.TaskManager.service.userServise;

import com.example.TaskManager.castomException.NotFoundUsernameException;
import com.example.TaskManager.entity.User;

public interface UserServise {
     User createUser (User user) throws NotFoundUsernameException;
     User findByUsername (String username) throws NotFoundUsernameException;

}
