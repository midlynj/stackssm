package com.example.stacks.interfaces;

import com.example.stacks.payload.AdminAccess;
import org.springframework.http.ResponseEntity;

public interface AdminInterface {
    ResponseEntity<?> fetchAllUsers(AdminAccess adminEmail);
    void deleteUser(Long id);
    void restrictUser(Long id);
    void activateUser(Long id);
}
