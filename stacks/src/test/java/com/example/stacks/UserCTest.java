package com.example.stacks;

import com.example.stacks.controller.UserController;
import com.example.stacks.payload.MessageResponse;
import com.example.stacks.payload.Signup;
import com.example.stacks.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    void healthCheckTest() throws Exception {
//    Controller testing
        when(service.greet()).thenReturn("Hello, World");
        this.mockMvc.perform(get("/api/users")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(("Hello, World")));
    }

    @Test
    public void getAllEmployeesAPI() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/users/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.users").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.users[*].userId").isNotEmpty());
    }

    @Test
    void healthCheckTestas() throws Exception {
//    Controller testing
        Set<String> strRoles = new HashSet<>();

        strRoles.add("ADMIN");

        Signup newUser = new Signup("Joey", "Stacks", "joey@gmail.com", "123", strRoles);

    }
}


