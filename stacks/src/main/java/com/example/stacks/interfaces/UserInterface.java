package com.example.stacks.interfaces;

import com.example.stacks.dto.UserDto;
import com.example.stacks.entity.User;
import com.example.stacks.payload.SignIn;
import com.example.stacks.payload.Signup;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserInterface {
    List<UserDto> fetchAllUsers();
    User fetchUserById(Long id);
    ResponseEntity<?> createNewUser(Signup newUser);
    ResponseEntity<?> authenticateUser(SignIn signInCredentials);

}
