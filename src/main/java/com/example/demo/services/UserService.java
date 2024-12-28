package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findUserByUsername(String username){

        Optional<User> user = userRepository.findByUsername(username);

        return user;
    }

    public Optional<User> findUserByEmail(String email){

        Optional<User> user = userRepository.findByEmail(email);

        return user;
    }
}
