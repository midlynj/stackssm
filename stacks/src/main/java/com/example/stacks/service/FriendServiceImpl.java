package com.example.stacks.service;

import com.example.stacks.component.DtoMapper;
import com.example.stacks.component.UserMapper;
import com.example.stacks.dto.FriendDto;
import com.example.stacks.dto.UserDto4;
import com.example.stacks.dto.UserDto;
import com.example.stacks.entity.User;
import com.example.stacks.interfaces.FriendInterface;
import com.example.stacks.repository.FriendRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FriendServiceImpl implements FriendInterface {
    private final FriendRepository friendRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(FriendServiceImpl.class);

    @Override
    public List<UserDto> fetchUserFriends() {
      return DtoMapper.toRepositoryDto(friendRepository.findAll(),UserDto.class);
    }


    @Override
    public UserDto fetchUserFriendById(Long friendId) {
        Optional<User> optionalUser = friendRepository.findById(friendId);

        if (optionalUser.isEmpty()) {
            LOGGER.error("User not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User " + friendId + " not found");
        }

        return DtoMapper.toDto(optionalUser.get(), UserDto.class);
    }

    @Override
    public ResponseEntity<?> addAFriend(Long myId, Long friendId) {
        Optional<User> userOptional = friendRepository.findById(myId);
        Optional<User> friendOptional = friendRepository.findById(friendId);

        if (userOptional.isEmpty() || friendOptional.isEmpty()) {
            LOGGER.error("User not found");
            throw new RuntimeException("User not found");
        }

        User userExist = userOptional.get();
        User friendExist = friendOptional.get();

        if (userExist.getFriends().contains(friendExist)) {
            LOGGER.error("Add Friend Failed: Users already friends");
            throw new RuntimeException("User: " + userExist.getFirstName() + " " + userExist.getLastName() + " is already friends with " + friendExist.getFirstName() + " " + friendExist.getLastName());
        }

        List<FriendDto> friendDtoList = UserMapper.toFriendDtoList(userExist);

        FriendDto friendDto = DtoMapper.toDto(friendExist, FriendDto.class);

        UserDto4 userDto = DtoMapper.toDto(userExist, UserDto4.class);

        friendDtoList.add(friendDto);
        friendRepository.addFriendFromUser(myId, friendId);
        friendRepository.addFriendFromUser(friendId, myId);
        userDto.setFriends(friendDtoList);

        LOGGER.info("Friend successfully added");

        return ResponseEntity.ok(userDto);

    }

    @Override
    public ResponseEntity<?> removeAFriend(Long myId, Long friendId) {
        Optional<User> userOptional = friendRepository.findById(myId);
        Optional<User> friendOptional = friendRepository.findById(friendId);

        if (userOptional.isEmpty()|| friendOptional.isEmpty()) {
            LOGGER.error("User not found");
            throw new RuntimeException("User does not exist");
        }

        User user = userOptional.get();
        User friendUser = friendOptional.get();

        if (!user.getFriends().contains(friendUser)) {
            LOGGER.error("Removed Friend Failed: Users are not friends");
            throw new RuntimeException("User: " + user.getFirstName() + " " + user.getLastName() + " " + "is not friends with " + friendUser.getFirstName() + " " + friendUser.getLastName());
        }

        List<FriendDto> friendDtoList = UserMapper.toFriendDtoList(user);

        FriendDto friendDto = DtoMapper.toDto(friendUser, FriendDto.class);

        UserDto4 userDto = DtoMapper.toDto(user, UserDto4.class);

        friendDtoList.remove(friendDto);

        friendRepository.deleteFriendFromUser(myId, friendId);
        friendRepository.deleteFriendFromUser(friendId, myId);
        userDto.setFriends(friendDtoList);

        LOGGER.info("Friend successfully removed");

        return ResponseEntity.ok(userDto);
    }

}
