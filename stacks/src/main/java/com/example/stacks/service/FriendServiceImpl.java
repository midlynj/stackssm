package com.example.stacks.service;

import com.example.stacks.dto.FriendDto;
import com.example.stacks.dto.UserDto4;
import com.example.stacks.dto.UserDto;
import com.example.stacks.entity.User;
import com.example.stacks.interfaces.FriendInterface;
import com.example.stacks.repository.FriendRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FriendServiceImpl implements FriendInterface {
    private final FriendRepository friendRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(FriendServiceImpl.class);

    @Override
    public List<UserDto> fetchUserFriends() {
      return fetchEntitiesWithMapper(friendRepository,User.class,UserDto.class);
    }

    public <T, D> List<D> fetchEntitiesWithMapper(JpaRepository<T, Long> repository, Class<T> entityClass, Class<D> dtoClass) {
        List<T> entities = repository.findAll();
        List<D> dtos = new ArrayList<>();

        for (T entity : entities) {
            D dto = mapEntityToDto(entity, dtoClass);
            dtos.add(dto);
        }

        return dtos;
    }

    private <T, D> D mapEntityToDto(T entity, Class<D> dtoClass) {
        try {
            D dto = dtoClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        } catch (Exception e) {

            return null;
        }
    }

    @Override
    public UserDto fetchUserFriendById(Long friendId) {
        Optional<User> optionalUser = friendRepository.findById(friendId);

        if (optionalUser.isEmpty()) {
            LOGGER.error("User not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User " + friendId + " not found");
        }

        User user = optionalUser.get();

        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getFriends());
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

        List<FriendDto> friendDtoList = userExist.getFriends().stream()
                .map(user -> {
                    FriendDto friendDto = new FriendDto();
                    friendDto.setId(user.getId());
                    friendDto.setFirstName(user.getFirstName());
                    friendDto.setEmail(user.getEmail());
                    friendDto.setLastName(user.getLastName());
                    return friendDto;
                })
                .collect(Collectors.toList());

        FriendDto friendDto = new FriendDto(friendExist.getId(), friendExist.getFirstName(), friendExist.getLastName(), friendExist.getEmail());

        UserDto4 userDto = new UserDto4();
        userDto.setId(userExist.getId());
        userDto.setFirstName(userExist.getFirstName());
        userDto.setLastName(userExist.getLastName());
        userDto.setEmail(userExist.getEmail());
        userDto.setRole(userExist.getRoles());
        userDto.setPosts(userExist.getPosts());

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

        List<FriendDto> friendDtoList = user.getFriends().stream().map(user1 -> {
            FriendDto friendDto = new FriendDto();
            friendDto.setId(user1.getId());
            friendDto.setFirstName(user1.getFirstName());
            friendDto.setEmail(user1.getEmail());
            friendDto.setLastName(user1.getLastName());
            return friendDto;
        }).collect(Collectors.toList());

        FriendDto friendDto = new FriendDto(friendUser.getId(), friendUser.getFirstName(), friendUser.getLastName(), friendUser.getEmail());

        UserDto4 userDto = new UserDto4();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRoles());
        userDto.setPosts(user.getPosts());

        friendDtoList.remove(friendDto);

        friendRepository.deleteFriendFromUser(myId, friendId);
        friendRepository.deleteFriendFromUser(friendId, myId);
        userDto.setFriends(friendDtoList);

        LOGGER.info("Friend successfully removed");

        return ResponseEntity.ok(userDto);
    }

}
