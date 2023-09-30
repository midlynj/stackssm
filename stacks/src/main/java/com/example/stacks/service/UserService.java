package com.example.stacks.service;

import com.example.stacks.entity.User;
import com.example.stacks.payload.MessageResponse;
import com.example.stacks.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public List<User> fetchAllUsers() {
        return userRepository.findAll();
    }
    public ResponseEntity<?> createNewUser(@RequestBody User newUser) {
        Optional<User> userOptional = userRepository.findUserByEmail(newUser.getEmail());

        if (userOptional.isPresent())
            throw new RuntimeException("Email is already in use");

        userRepository.save(newUser);

        return ResponseEntity.ok(new MessageResponse("New user registered"));
    }
}
