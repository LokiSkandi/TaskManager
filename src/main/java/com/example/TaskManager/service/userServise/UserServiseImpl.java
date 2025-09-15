package com.example.TaskManager.service.userServise;

import com.example.TaskManager.castomException.NotFoundUsernameException;
import com.example.TaskManager.entity.User;
import com.example.TaskManager.repository.UserRepository;

public class UserServiseImpl implements UserServise {
    UserRepository rep;


    @Override
    public User createUser(User user) throws NotFoundUsernameException {
        if(rep.existsByUsername(user.getUsername())) {
            throw new NotFoundUsernameException("Пользователь с таким именем уже существует!");
        }
        return rep.save(user);
    }

    @Override
    public User findByUsername(String username) throws NotFoundUsernameException {
        return rep.findByUsername(username).orElseThrow(() -> new NotFoundUsernameException("Пользователя с таким именем не существует!"));
    }
}
