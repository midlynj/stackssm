package com.example.stacks;

import com.example.stacks.controller.UserController;
import com.example.stacks.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserCTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl service;

    @Test
    void greetingShouldReturnMessageFromService() throws Exception {
//    Controller testing
        when(service.greet()).thenReturn("Hello, World");
        this.mockMvc.perform(get("/api/users/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, World")));
    }
}


