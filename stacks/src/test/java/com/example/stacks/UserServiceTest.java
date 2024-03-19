package com.example.stacks;

import com.example.stacks.dto.UserDto;
import com.example.stacks.entity.*;
import com.example.stacks.payload.SignIn;
import com.example.stacks.payload.Signup;
import com.example.stacks.repository.ImageRepository;
import com.example.stacks.repository.RoleRepository;
import com.example.stacks.repository.UserRepository;
import com.example.stacks.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class UserServiceTest {
//    The @Mock annotation creates a mock implementation for the class it is annotated with.
//    @InjectMocks also creates the mock implementation of annotated type and injects the dependent mocks into it.
    @InjectMocks
    UserServiceImpl service;

    @Mock
    UserRepository dao;
    @Mock
    ImageRepository imageRepository;
    @Mock
    RoleRepository roleRepository;
    @Mock
    BCryptPasswordEncoder passwordEncoder;
    List<User> userList = new ArrayList<>();
    private User newUser;

    @BeforeEach
    void setUp() {
        newUser = new User(1L, "a", "s", "a@email.com", "123", null, null, null, Status.ACTIVE, null);
        userList.add(newUser);
    }

    @Test
    void testFindAllUsers() {
        when(dao.findAll()).thenReturn(userList);

        List<UserDto> userDtoList = service.fetchAllUsers();

        assertEquals(1, userDtoList.size());
        assertEquals("a", userDtoList.get(0).getFirstName());
        assertEquals("s", userDtoList.get(0).getLastName());
        assertEquals("a@email.com", userDtoList.get(0).getEmail());
        assertEquals(1, userDtoList.size());

        verify(dao, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(dao.findById(1L)).thenReturn(Optional.ofNullable(newUser));

        User userFound = service.fetchUserById(1L);

        assertEquals(newUser, userFound);
        assertEquals(newUser.getId(), userFound.getId());
        assertEquals(newUser.getFirstName(), userFound.getFirstName());
        assertEquals(newUser.getLastName(), userFound.getLastName());

        verify(dao, times(1)).findById(1L);
    }


    @Test
    void testSignIn() {
        when(dao.findUserByEmail("a@email.com")).thenReturn(Optional.ofNullable(newUser));
        when(passwordEncoder.matches("123", newUser.getPassword())).thenReturn(true); // Stub password encoder behavior

        SignIn signIn = new SignIn("a@email.com", "123");
        ResponseEntity<?> user = service.authenticateUser(signIn);

        assertEquals(HttpStatus.OK, user.getStatusCode());
        verify(dao, times(1)).findUserByEmail("a@email.com");
    }

    @Test
    void testCreateUser() {
        when(imageRepository.findById(1L)).thenReturn(Optional.of(new Image(1L,null)));
        when(roleRepository.findByName(ERole.USER)).thenReturn(Optional.of(new Role(1L, ERole.USER)));
        when(passwordEncoder.encode("123")).thenReturn("123encoded");

        Signup newUser = new Signup("joey","stacks","joey@email.com","123",null);
        ResponseEntity<?> response = service.createNewUser(newUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(dao, times(1)).save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();
        Set<Role> roles = capturedUser.getRoles();

// Convert the set to a stream, map each role to its name, and collect to a set of role names
        Set<ERole> roleNames = roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        assertTrue(roleNames.contains(ERole.USER));
        assertEquals(Status.ACTIVE, capturedUser.getStatus());
        assertEquals("123encoded", capturedUser.getPassword());
    }
}
