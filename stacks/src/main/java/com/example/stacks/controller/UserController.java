package com.example.stacks.controller;

import com.example.stacks.dto.FriendDto;
import com.example.stacks.dto.UserDto;
import com.example.stacks.payload.Signup;
import com.example.stacks.entity.User;
import com.example.stacks.payload.SignIn;
import com.example.stacks.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/users", headers = "Accept=application/json")
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping()
    public  String greeting() {
        return userService.greet();
    }


    @GetMapping("/all")
    public List<UserDto> fetchAllUsers() {
        return userService.fetchAllUsers();
    }

    @GetMapping("/{id}")
    public User fetchUserById(@PathVariable Long id) {
        return userService.fetchUserById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody Signup newUser) {
        return userService.createNewUser(newUser);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody SignIn signInCredentials) {
        return userService.authenticateUser(signInCredentials);
    }
}
