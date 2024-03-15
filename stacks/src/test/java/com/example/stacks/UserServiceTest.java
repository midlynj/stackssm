package com.example.stacks;

import com.example.stacks.dto.UserDto;
import com.example.stacks.entity.ERole;
import com.example.stacks.entity.Role;
import com.example.stacks.entity.Status;
import com.example.stacks.entity.User;
import com.example.stacks.payload.SignIn;
import com.example.stacks.payload.Signup;
import com.example.stacks.repository.ImageRepository;
import com.example.stacks.repository.UserRepository;
import com.example.stacks.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


import java.util.*;

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

        List<UserDto> empList = service.fetchAllUsers();

        assertEquals(1, empList.size());
        assertEquals("a", empList.get(0).getFirstName());
        assertEquals("s", empList.get(0).getLastName());
        assertEquals("a@email.com", empList.get(0).getEmail());

        verify(dao, times(1)).findAll();
        assertEquals(1, empList.size());

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
    void testCreateUser() {
        Set<Role> roleSet = Collections.singleton(new Role(1L, ERole.USER));

        Signup newSignup = new Signup("Kair", "St", "kair@gmail.com", "123", null);
        User userFromSignUp = new User();
        userFromSignUp.setId(2L);
        userFromSignUp.setFirstName(newSignup.getFirstName());
        userFromSignUp.setLastName(newSignup.getLastName());
        userFromSignUp.setPassword(newSignup.getPassword());
        userFromSignUp.setEmail(newSignup.getEmail());
        userFromSignUp.setRoles(roleSet);
        userList.add(userFromSignUp);

        dao.save(userFromSignUp);

        assertEquals(2, userList.size());
        assertEquals("a", userList.get(0).getFirstName());
        assertEquals("Kair", userList.get(1).getFirstName());
        assertEquals(1, userList.get(0).getId());
        assertEquals(2, userList.get(1).getId());

        verify(dao, times(1)).save(userFromSignUp);
    }

    @Test
    void testSignIn() {
        SignIn signIn = new SignIn("a@email.com", "123");
//test
        assertEquals(newUser.getEmail(), signIn.getEmail());
        assertEquals(newUser.getPassword(), signIn.getPassword());
    }
}
