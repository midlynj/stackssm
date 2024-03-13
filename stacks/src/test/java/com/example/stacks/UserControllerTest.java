package com.example.stacks;

import com.example.stacks.entity.*;
import com.example.stacks.payload.MessageResponse;
import com.example.stacks.payload.Signup;
import com.example.stacks.repository.ImageRepository;
import com.example.stacks.repository.RoleRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.example.stacks.entity.ERole.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = StacksApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class UserControllerTest {
    @LocalServerPort
    private int port;
    private  String baseUrl = "http://localhost";
    private static RestTemplate restTemplate;

    @Autowired
    private TestH2Repository h2Repository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    RoleRepository roleRepository;

    private static Set<Role> roles = new HashSet<>();



//    Optional<Image> imageOptional = imageRepository.findById(1L);
//    Image defaultImage = imageOptional.get();

    Collection<Post> posts;
    List<User> userList;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port+"").concat("/api/users/create");
    }

    @Test
    public void newUser() {
//        Role userRole = roleRepository.findByName(ERole.USER).orElseThrow(() -> new RuntimeException("Error: Role not found"));
//        roles.add(userRole);
//        roles.add(USER);
//        User newUser = new User(1L,"Joey","Stack","j@gmail.com","123",posts,userList,null, Status.ACTIVE, null);
        Signup newUser = new Signup("joe","st","joe@gmail.com","123",null);
        MessageResponse response = restTemplate.postForObject(baseUrl,newUser, MessageResponse.class);
//        assertEquals("New user registered successfully", response);
        assertEquals(1, h2Repository.findAll().size());
    }

//    @Test
//    public void userList() {
//        User newUser = new User(1L,"Joey","Stack","j@gmail.com","123",posts,userList,null, Status.ACTIVE, null);
//        User response = restTemplate.getForObject(baseUrl,newUser,User.class);
//    }



}
