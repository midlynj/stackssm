package com.example.stacks.controller;

import com.example.stacks.dto.UserDto;
import com.example.stacks.service.FriendServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/friends", headers = "Accept=application/json")
public class FriendController {
    private final FriendServiceImpl friendService;

    @GetMapping("/all")
    public List<UserDto> fetchAllUserFriends() {
        return friendService.fetchUserFriends();
    }

    @GetMapping("/{friendId}")
    public UserDto fetchUserFriendsById(@PathVariable Long friendId) {
        return friendService.fetchUserFriendById(friendId);
    }

    @PostMapping("add-friend/{myId}/{friendId}")
    public ResponseEntity<?> addFriend(@PathVariable long myId, @PathVariable long friendId) {
       return friendService.addAFriend(myId, friendId);
    }

    @DeleteMapping("remove-friend/{myId}/{friendId}")
    public ResponseEntity<?> removeFriend(@PathVariable long myId, @PathVariable long friendId) {
        return friendService.removeAFriend(myId, friendId);
    }
}
