package com.example.TaskManager.service.userServise;

import com.example.TaskManager.dto.UserDetailsImpl;
import com.example.TaskManager.entity.User;
import com.example.TaskManager.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return UserDetailsImpl.build(userRepository.findByUsername(username).orElseThrow(UsernameNotFoundException);
    }
}
