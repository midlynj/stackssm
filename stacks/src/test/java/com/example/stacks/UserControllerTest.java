package com.example.stacks;

import com.example.stacks.controller.UserController;
import com.example.stacks.dto.UserDto;
import com.example.stacks.entity.Status;
import com.example.stacks.entity.User;
import com.example.stacks.payload.SignIn;
import com.example.stacks.payload.Signup;
import com.example.stacks.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @InjectMocks
    private UserController userController;
    @Mock
    private UserServiceImpl userService;
    private List<UserDto> userDtoList;

    private User user = new User(1L, "a", "s", "a@email.com", "123", null, null, null, Status.ACTIVE, null);


    @BeforeEach
    void setUp() {
        userDtoList = new ArrayList<>();
        userDtoList.add(new UserDto(1L, "j", "s", "j@email.com", null));
    }

    @Test
    public void testGetAllUsers() {
        when(userService.fetchAllUsers()).thenReturn(userDtoList);

        List<UserDto> response = userController.fetchAllUsers();

        assertEquals(1, response.size());
    }

    @Test
    public void testCreateUser() {
        Signup newUser = new Signup("joey","stacks","joey@email.com","123",null);

        when(userService.createNewUser(newUser)).thenReturn(ResponseEntity.ok().build());

        ResponseEntity<?> response = userController.createUser(newUser);

        assertEquals(HttpStatus.OK, response.getStatusCode() );
    }

    @Test
    public void testUserById() {
        when(userService.fetchUserById(1L)).thenReturn(user);

        User response = userController.fetchUserById(1L);

        assertEquals("a", response.getFirstName());
    }

    @Test
    public void testSignIn() {
        SignIn signIn = new SignIn("a@email.com", "123");

        when(userService.authenticateUser(signIn)).thenReturn(ResponseEntity.ok().build());

        ResponseEntity<?> response = userController.authenticateUser(signIn);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
