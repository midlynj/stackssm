package com.example.stacks.service;

import com.example.stacks.dto.UserDto;
import com.example.stacks.dto.UserDto2;
import com.example.stacks.payload.Signup;
import com.example.stacks.entity.User;
import com.example.stacks.exception.UserNotFoundException;
import com.example.stacks.payload.MessageResponse;
import com.example.stacks.payload.SignIn;
import com.example.stacks.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public List<UserDto> fetchAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDTOs = new ArrayList<>();

        for (User user : users) {
            UserDto userDTO = new UserDto();
            userDTO.setId(user.getId());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setEmail(user.getEmail());
            userDTO.setLastName(user.getLastName());
            userDTO.setFriends(user.getFriends());
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }

    public User fetchUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
    public ResponseEntity<?> createNewUser(Signup newUser) {
        Optional<User> userOptional = userRepository.findUserByEmail(newUser.getEmail());

        if (userOptional.isPresent())
            return ResponseEntity.badRequest().body(new MessageResponse("Email is already in use"));

        if (newUser.getPassword().length() <= 2)
            return ResponseEntity.badRequest().body(new MessageResponse("Password must be minimum 3 characters"));

        String hashedPassword = passwordEncoder.encode(newUser.getPassword());

        User user = new User();
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEmail(newUser.getEmail());
        user.setPassword(hashedPassword);


//        String hashedPassword = passwordEncoder.encode(newUser.getPassword());
//        newUser.setPassword(hashedPassword);

//        userRepository.save(newUser);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("New user registered successfully"));
    }

    public ResponseEntity<?> authenticateUser(SignIn signInCredentials) {
        Optional<User> authenticatedUser = userRepository.findUserByEmail(signInCredentials.getEmail());
        User userExist = authenticatedUser.get();
//        UserDto userDto = new UserDto(userExist.getId(), userExist.getFirstName(), userExist.getLastName(), userExist.getEmail(), userExist.getFriends());

//        if (userExist.getPassword().equals(signInCredentials.getPassword()))
//            return ResponseEntity.ok(userDto);
//        if (bCryptPass)

        UserDto2 userDto2 = new UserDto2(userExist.getId(), userExist.getFirstName(), userExist.getLastName(), userExist.getEmail(), userExist.getFriends(), userExist.getPosts());


        if (verifyPassword(signInCredentials.getPassword(), userExist.getPassword()))
            return ResponseEntity.ok(userDto2);



        return ResponseEntity.badRequest().body(new MessageResponse("Bad credentials"));
    }
}
