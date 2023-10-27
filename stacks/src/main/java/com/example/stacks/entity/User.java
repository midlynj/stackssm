package com.example.stacks.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "author")
    @JsonIgnoreProperties("author")
    private Collection<Post> posts;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_friends",
            joinColumns = { @JoinColumn(name = "user_id")},
            inverseJoinColumns={@JoinColumn(name="friend_id")})
    @JsonIgnoreProperties({"friends", "friendsList","createdAt", "posts","password"})
    @ToString.Exclude
    private List<User> friends;

    @OneToOne
    @JsonIgnoreProperties("image")
    @JoinColumn(name = "image_id")

    private Image image;

//    @OneToMany
//    @JsonIgnoreProperties("image")
//    @JoinColumn(name = "image_id")
//    private List<Image> image;
}
