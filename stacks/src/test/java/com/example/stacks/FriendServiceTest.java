package com.example.stacks;

import com.example.stacks.dto.UserDto;
import com.example.stacks.entity.Status;
import com.example.stacks.entity.User;
import com.example.stacks.repository.FriendRepository;
import com.example.stacks.service.FriendServiceImpl;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FriendServiceTest {
    @InjectMocks
    FriendServiceImpl service;
    @Mock
    FriendRepository dao;

    List<User> userList = new ArrayList<>();
    List<User> friendList = new ArrayList<>();
    private User user;
    User user3 = new User(3L, "user3firstname", "user3lastname", "user3@email.com", "123", null, null, null, Status.ACTIVE, null);

    @BeforeEach
    void setUp() {
         user = new User(1L, "userfirstname", "userlastname", "user@email.com", "123", null, null, null, Status.ACTIVE, null);
        User user2 = new User(2L, "user2firstname", "user2lastname", "user2@email.com", "123", null, null, null, Status.ACTIVE, null);

        friendList.add(user2);
        user.setFriends(friendList);

        userList.add(user);
        userList.add(user2);
    }

    @Test
    void testFindAllFriends() {
        when(dao.findAll()).thenReturn(userList);
        List<UserDto> userDto = service.fetchUserFriends();

        assertEquals(userList.get(0).getFirstName(), userDto.get(0).getFirstName());
        assertEquals("user2lastname", userDto.get(1).getLastName());

        verify(dao, times(1)).findAll();
        assertEquals(2, userDto.size());

    }

    @Test
    void testFindFriendById() {
        when(dao.findById(1L)).thenReturn(Optional.ofNullable(user));
        UserDto userDto = service.fetchUserFriendById(1L);
        System.out.println(userDto);

        verify(dao, times(1)).findById(1L);
        assertEquals(user.getFirstName(), userDto.getFirstName());
    }

    @Test
    void testAddFriend() {
       when(dao.findById(1L)).thenReturn(Optional.ofNullable(user));
       when(dao.findById(3L)).thenReturn(Optional.ofNullable(user3));

       ResponseEntity<?> response = service.addAFriend(1L,3L);

       assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testRemoveFriend() {
        when(dao.findById(1L)).thenReturn(Optional.ofNullable(user));
        when(dao.findById(2L)).thenReturn(Optional.ofNullable(userList.get(1)));

        ResponseEntity<?> response = service.removeAFriend(1L,2L);

        verify(dao, times(1)).findById(1L);
        verify(dao, times(1)).findById(2L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
