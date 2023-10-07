package com.example.stacks.dto;

import com.example.stacks.entity.Post;
import com.example.stacks.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto2 {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    @JsonIgnoreProperties({"friends", "friendsList","createdAt", "posts","password"})
    @ToString.Exclude
    private List<User> friends;

    @JsonIgnoreProperties("author")
    private Collection<Post> posts;
}
