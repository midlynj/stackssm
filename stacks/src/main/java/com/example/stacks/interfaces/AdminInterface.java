package com.example.stacks.interfaces;

import com.example.stacks.payload.AdminAccess;
import org.springframework.http.ResponseEntity;

public interface AdminInterface {
    ResponseEntity<?> fetchAllUsers(AdminAccess adminEmail);
    ResponseEntity<?> deleteUser(Long id);
    ResponseEntity<?> restrictUser(Long id);
    ResponseEntity<?> activateUser(Long id);
}
