package com.example.stacks.service;


import com.example.stacks.dto.UserDto;
import com.example.stacks.entity.User;
import com.example.stacks.interfaces.FriendInterface;
import com.example.stacks.repository.FriendRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FriendServiceImpl implements FriendInterface {
    private final FriendRepository friendRepository;

    @Override
    public List<UserDto> fetchUserFriends() {
        List<User> users = friendRepository.findAll();
        List<UserDto> userDTOs = new ArrayList<>();

        for (User user : users) {
            UserDto userDTO = new UserDto();
            userDTO.setId(user.getId());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setEmail(user.getEmail());
            userDTO.setLastName(user.getLastName());
            userDTO.setFriends(user.getFriends());
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }

    @Override
    public UserDto fetchUserFriendById(Long friendId) {
        Optional<User> optionalUser = friendRepository.findById(friendId);

        if (optionalUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User " + friendId + " not found");

        User user = optionalUser.get();

        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getFriends());
    }

    @Override
    public void addAFriend(Long myId, Long friendId) {
        friendRepository.addFriendFromUser(myId, friendId);
        friendRepository.addFriendFromUser(friendId, myId);
    }

}
