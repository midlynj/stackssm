package com.example.stacks.service;

import com.example.stacks.dto.UserDto;
import com.example.stacks.dto.UserDto2;
import com.example.stacks.entity.*;
import com.example.stacks.interfaces.UserInterface;
import com.example.stacks.payload.Signup;
import com.example.stacks.exception.UserNotFoundException;
import com.example.stacks.payload.MessageResponse;
import com.example.stacks.payload.SignIn;
import com.example.stacks.repository.ImageRepository;
import com.example.stacks.repository.RoleRepository;
import com.example.stacks.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserInterface {
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final RoleRepository roleRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    @Override
    public List<UserDto> fetchAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> {
            UserDto userDTO = new UserDto();
            userDTO.setId(user.getId());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setEmail(user.getEmail());
            userDTO.setLastName(user.getLastName());
            userDTO.setFriends(user.getFriends());
            return userDTO;
        })    .collect(Collectors.toList());
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

        if (!patternMatches(newUser.getEmail(), "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            LOGGER.error("Invalid email format: User creation failed");
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid email format"));
        }

        if (userOptional.isPresent()) {
            LOGGER.error("Duplicated email: User creation failed");
            return ResponseEntity.badRequest().body(new MessageResponse("Email is already in use"));
        }

        if (newUser.getPassword().length() <= 2) {
            LOGGER.error("Password does not meet requirements: User creation failed");
            return ResponseEntity.badRequest().body(new MessageResponse("Password must be minimum 3 characters"));
        }

        String hashedPassword = passwordEncoder.encode(newUser.getPassword());

        User user = new User();
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEmail(newUser.getEmail());
        user.setPassword(hashedPassword);
        user.setUserPicture(defaultImage.getUserDefault());

        Set<String> strRoles = newUser.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.USER).orElseThrow(() -> new RuntimeException("Error: Role not found"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if (role.equals("ADMIN")) {
                    Role adminRole = roleRepository.findByName(ERole.ADMIN).orElseThrow(() -> new RuntimeException("Error: Role not found"));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findByName(ERole.USER).orElseThrow(() -> new RuntimeException("Error: Role not found "));
                    roles.add(userRole);
                }

            });
        }

        user.setRoles(roles);
        user.setStatus(Status.ACTIVE);
        userRepository.save(user);

        LOGGER.info("New user successfully created");

        return ResponseEntity.ok(new MessageResponse("New user registered successfully"));
    }

    @Override
    public ResponseEntity<?> authenticateUser(SignIn signInCredentials) {
        Optional<User> userOptional = userRepository.findUserByEmail(signInCredentials.getEmail());

        if (userOptional.isEmpty()) {
            LOGGER.error("User not found");
            throw new RuntimeException("User does not exist");
        }

        User userExist = userOptional.get();

        if (userExist.getStatus().equals(Status.INACTIVE)) {
            LOGGER.error("User account locked: Sign in failed");
            return ResponseEntity.badRequest().body(new MessageResponse("Account is locked"));
        }

        if (!verifyPassword(signInCredentials.getPassword(), userExist.getPassword())) {
            LOGGER.error("Bad credentials: Sign in failed");
            return ResponseEntity.badRequest().body(new MessageResponse("Bad credentials"));
        }

        UserDto2 userDto = new UserDto2(userExist.getId(), userExist.getFirstName(), userExist.getLastName(), userExist.getEmail(),userExist.getRoles(), userExist.getFriends(), userExist.getPosts());

        LOGGER.info("User successfully signed in");

        return ResponseEntity.ok(userDto);
    }
}
