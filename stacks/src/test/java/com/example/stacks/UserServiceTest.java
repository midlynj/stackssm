package com.example.stacks;

import com.example.stacks.dto.UserDto;
import com.example.stacks.entity.ERole;
import com.example.stacks.entity.Role;
import com.example.stacks.entity.Status;
import com.example.stacks.entity.User;
import com.example.stacks.payload.SignIn;
import com.example.stacks.payload.Signup;
import com.example.stacks.repository.UserRepository;
import com.example.stacks.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
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
    User newUser = new User(1L,"a","s","a@email.com","123",null,null,null, Status.ACTIVE,null);

    @Test
    void testFindAllUsers() {
        userList.add(newUser);
        when(dao.findAll()).thenReturn(userList);

        List<UserDto> empList = service.fetchAllUsers();

        assertEquals(1, empList.size());
        assertEquals("a", empList.get(0).getFirstName());
        assertEquals("s", empList.get(0).getLastName());
        assertEquals("a@email.com", empList.get(0).getEmail());

        verify(dao, times(1)).findAll();
    }

    @Test
    void testFindById() {
        userList.add(newUser);
        when(dao.findById(1L)).thenReturn(Optional.ofNullable(newUser));

        User userFound = service.fetchUserById(1L);

        assertEquals(newUser,userFound);
        verify(dao, times(1)).findById(1L);
    }

    @Test
    void testCreateUser() {
        Set<Role> roleSet = new HashSet<>();
        Role userRole = new Role(1L,ERole.USER);

         roleSet.add(userRole);

        Signup newSignup = new Signup("Kair","St","kair@gmail.com","123",null);

        User userFromSignUp = new User();
        userFromSignUp.setId(1L);
        userFromSignUp.setFirstName(newSignup.getFirstName());
        userFromSignUp.setLastName(newSignup.getLastName());
        userFromSignUp.setPassword(newSignup.getPassword());
        userFromSignUp.setEmail(newSignup.getEmail());
        userFromSignUp.setRoles(roleSet);
        System.out.println(userFromSignUp);

        dao.save(userFromSignUp);

        verify(dao, times(1)).save(userFromSignUp);
    }

    @Test
    void testSignIn() {
        userList.add(newUser);

        SignIn signIn = new SignIn("a@email.com","123");

        assertEquals(newUser.getEmail(),signIn.getEmail());
        assertEquals(newUser.getPassword(),signIn.getPassword());

    }
}
