package com.example.stacks.service;

import com.example.stacks.entity.User;
import com.example.stacks.exception.UserNotFoundException;
import com.example.stacks.payload.MessageResponse;
import com.example.stacks.payload.SignIn;
import com.example.stacks.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public List<User> fetchAllUsers() {
        return userRepository.findAll();
    }

    public User fetchUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
    public ResponseEntity<?> createNewUser(User newUser) {
        Optional<User> userOptional = userRepository.findUserByEmail(newUser.getEmail());

        if (userOptional.isPresent())
            return ResponseEntity.badRequest().body(new MessageResponse("Email is already in use"));

        userRepository.save(newUser);

        return ResponseEntity.ok(new MessageResponse("New user registered successfully"));
    }

//    public ResponseEntity<?> authenticateUser(User user) {
//        Optional<User> authenticatedUser = userRepository.findUserByEmail(user.getEmail());
//        User userExist = authenticatedUser.get();
//
//        if (userExist.getPassword().equals(user.getPassword()))
//            return ResponseEntity.ok(userExist);
//
//        return ResponseEntity.badRequest().body(new MessageResponse("Bad credentials"));
//
//    }

    public ResponseEntity<?> authenticateUser(SignIn signInCredentials) {
        Optional<User> authenticatedUser = userRepository.findUserByEmail(signInCredentials.getEmail());
        User userExist = authenticatedUser.get();

        if (userExist.getPassword().equals(signInCredentials.getPassword()))
            return ResponseEntity.ok(userExist);

        return ResponseEntity.badRequest().body(new MessageResponse("Bad credentials"));
    }
}
