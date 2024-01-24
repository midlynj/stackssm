package com.example.stacks.dto;

import com.example.stacks.entity.Post;
import com.example.stacks.entity.Role;
import com.example.stacks.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto2 {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<Role> role;

    @JsonIgnoreProperties({"friends", "friendsList","createdAt", "posts","password","status","roles"})
    private List<User> friends;

    @JsonIgnoreProperties("author")
    private Collection<Post> posts;
}
