package com.example.stacks.service;

import com.example.stacks.dto.UserDto;
import com.example.stacks.dto.UserDto2;
import com.example.stacks.entity.Image;
import com.example.stacks.interfaces.UserInterface;
import com.example.stacks.payload.Signup;
import com.example.stacks.entity.User;
import com.example.stacks.exception.UserNotFoundException;
import com.example.stacks.payload.MessageResponse;
import com.example.stacks.payload.SignIn;
import com.example.stacks.repository.ImageRepository;
import com.example.stacks.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserInterface {
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
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

    @Override
    public User fetchUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public ResponseEntity<?> createNewUser(Signup newUser) {
        Optional<User> userOptional = userRepository.findUserByEmail(newUser.getEmail());

        Optional<Image> imageOptional = imageRepository.findById(1L);
        Image defaultImage = imageOptional.get();

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
        user.setUserPicture(defaultImage.getUserDefault());
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("New user registered successfully"));
    }

    @Override
    public ResponseEntity<?> authenticateUser(SignIn signInCredentials) {
        Optional<User> userOptional = userRepository.findUserByEmail(signInCredentials.getEmail());
        User userExist = userOptional.get();

        UserDto2 userDto2 = new UserDto2(userExist.getId(), userExist.getFirstName(), userExist.getLastName(), userExist.getEmail(), userExist.getFriends(), userExist.getPosts());

        if (verifyPassword(signInCredentials.getPassword(), userExist.getPassword()))
            return ResponseEntity.ok(userDto2);

        return ResponseEntity.badRequest().body(new MessageResponse("Bad credentials"));
    }
}
