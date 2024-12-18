package com.example.stacks;

import com.example.stacks.controller.FriendController;
import com.example.stacks.dto.UserDto;
import com.example.stacks.entity.Status;
import com.example.stacks.entity.User;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FriendControllerTest {
    @InjectMocks
    private FriendController friendController;
    @Mock
    private FriendServiceImpl friendService;

    List<User> friendList = new ArrayList<>();
    List<UserDto> userDtoList = new ArrayList<>();
    UserDto userDto = new UserDto(1L, "user3firstname", "user3lastname", "user3@email.com", friendList);


    @BeforeEach
    void setUp() {
        User user2 = new User(2L, "user2firstname", "user2lastname", "user2@email.com", "123", null, null, null, Status.ACTIVE, null);
        friendList.add(user2);
        userDtoList.add(userDto);
    }

    @Test
    void testFindAllUsers() {
        when(friendService.fetchUserFriends()).thenReturn(userDtoList);
        List<UserDto> userDtos = friendController.fetchAllUserFriends();
        assertEquals(1, userDtos.size());
        assertEquals("user3firstname", userDtos.get(0).getFirstName());
        verify(friendService, times(1)).fetchUserFriends();
    }

    @Test
    void testFindUserById() {
        when(friendService.fetchUserFriendById(1L)).thenReturn(userDto);
        UserDto response = friendController.fetchUserFriendsById(1L);
        assertEquals("user3firstname", response.getFirstName());
        assertEquals("user2firstname", response.getFriends().get(0).getFirstName());
        verify(friendService, times(1)).fetchUserFriendById(1L);
    }

    @Test
    void testAddFriend() {
        when(friendService.addAFriend(1L, 2L)).thenReturn(ResponseEntity.ok().build());
        ResponseEntity<?> response =  friendController.addFriend(1L, 2L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(friendService, times(1)).addAFriend(1L, 2L);
    }

    @Test
    void testRemoveFriend() {
        when(friendService.removeAFriend(1L, 2L)).thenReturn(ResponseEntity.ok().build());
        ResponseEntity<?> response =  friendController.removeFriend(1L, 2L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(friendService, times(1)).removeAFriend(1L, 2L);
    }

}
