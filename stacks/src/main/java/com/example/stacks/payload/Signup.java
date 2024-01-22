package com.example.stacks.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Signup {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<String> role;
}
