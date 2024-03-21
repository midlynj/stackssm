package com.example.stacks;

import com.example.stacks.controller.UserController;
import com.example.stacks.dto.UserDto;
import com.example.stacks.entity.ERole;
import com.example.stacks.entity.Role;
import com.example.stacks.entity.Status;
import com.example.stacks.entity.User;
import com.example.stacks.payload.MessageResponse;
import com.example.stacks.payload.Signup;
import com.example.stacks.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest(UserController.class)
@SpringBootTest
@AutoConfigureMockMvc

public class UserCTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl service;

    List<UserDto> userDtoList = new ArrayList<>();

    @BeforeEach
    void setUp() {
       UserDto userDto = new UserDto(1L,"j","s","j@email.com",null);
       userDtoList.add(userDto);
    }


    @Test
    void healthCheckTest() throws Exception {
//    Controller testing since Im mocking the response
        when(service.greet()).thenReturn("Hello, World");
        this.mockMvc.perform(get("/api/users")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(("Hello, World")));
    }

    @Test
    @Sql("/m.sql")
    public void testGetAllUsers() throws Exception {
        // Perform GET request to retrieve all users
        mockMvc.perform(get("/api/users/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Danie"));
    }

//    @Test
//    public void getAllEmployeesAPI() throws Exception
//    {
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/users/all")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.users").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.users[*].userId").isNotEmpty());
//    }


}


