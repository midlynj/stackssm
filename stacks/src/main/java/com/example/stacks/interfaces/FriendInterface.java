package com.example.stacks.interfaces;

import com.example.stacks.dto.UserDto;

import java.util.List;

public interface FriendInterface {
    List<UserDto> fetchUserFriends();
    UserDto fetchUserFriendById(Long friendId);
    void addAFriend(Long myId, Long friendId);
}
