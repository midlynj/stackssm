package com.example.stacks.interfaces;

import com.example.stacks.dto.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FriendInterface {
    List<UserDto> fetchUserFriends();
    UserDto fetchUserFriendById(Long friendId);
    ResponseEntity<?> addAFriend(Long myId, Long friendId);
    ResponseEntity<?> removeAFriend(Long myId, Long friendId);
}
