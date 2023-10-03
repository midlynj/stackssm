package com.example.stacks.controller;

import com.example.stacks.entity.User;
import com.example.stacks.payload.SignIn;
import com.example.stacks.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/users", headers = "Accept=application/json")
public class UserController {
    private UserService userService;

    @GetMapping("/all")
    public List<User> fetchAllUsers() {
        return userService.fetchAllUsers();
    }

    @GetMapping("/{id}")
    public User fetchUserById(@PathVariable Long id) {
        return userService.fetchUserById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User newUser) {
        return userService.createNewUser(newUser);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody SignIn signInCredentials) {
        return userService.authenticateUser(signInCredentials);
    }
}
