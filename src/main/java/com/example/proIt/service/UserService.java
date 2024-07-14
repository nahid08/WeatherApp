package com.example.proIt.service;


import com.example.proIt.model.User;
import com.example.proIt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void save(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

    }

    public boolean isUserExist(String name) {
        Optional<User> oldUser = userRepository.findByUsername(name);
        if(oldUser.isPresent()) {
            return true;
        }
        return false;
    }

    public Optional<User> getUserByName(String name) {
        return userRepository.findByUsername(name);
    }
}
