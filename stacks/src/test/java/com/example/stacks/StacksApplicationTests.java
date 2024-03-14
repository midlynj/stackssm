package com.example.stacks;

import com.example.stacks.controller.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class StacksApplicationTests {


    @Autowired
    private UserController userController;
    @Autowired
    private AdminController adminController;
    @Autowired
    private FriendController friendController;
    @Autowired
    private PostController postController;
    @Autowired
    private ImageController imageController;


    @Test
    void contextLoads() throws Exception {
//        Sanity Check
        assertThat(userController).isNotNull();
        assertThat(adminController).isNotNull();
        assertThat(friendController).isNotNull();
        assertThat(postController).isNotNull();
        assertThat(imageController).isNotNull();

    }

}
