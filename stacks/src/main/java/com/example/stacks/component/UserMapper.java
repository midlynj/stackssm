package com.example.stacks.component;

import com.example.stacks.dto.FriendDto;
import com.example.stacks.dto.UserDto4;
import com.example.stacks.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public static List<FriendDto> toFriendDtoList(User user) {
        if (user == null)
            return null;

        return user.getFriends().stream().map(user1 -> {
            // FriendDto friendDto = new FriendDto();
            // friendDto.setId(user1.getId());
            // friendDto.setFirstName(user1.getFirstName());
            // friendDto.setEmail(user1.getEmail());
            // friendDto.setLastName(user1.getLastName());
            return DtoMapper.toDto(user1, FriendDto.class);
        }).collect(Collectors.toList());
    }

    public static FriendDto toFriendDto(User user) {
        if (user == null)
            return null;

        return new FriendDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
    }

    public static UserDto4 toUserDto4(User user) {
        if (user == null)
            return null;

        UserDto4 userDto = new UserDto4();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRoles());
        userDto.setPosts(user.getPosts());

        return userDto;
    }
}
