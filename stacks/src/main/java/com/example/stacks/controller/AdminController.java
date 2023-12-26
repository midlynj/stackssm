package com.example.stacks.controller;

import com.example.stacks.payload.AdminAccess;
import com.example.stacks.service.AdminServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/admin", headers = "Accept=application/json")
public class AdminController {
   private final AdminServiceImpl adminService;

    @GetMapping("/all/users")
    public ResponseEntity<?> fetchAllUsers(AdminAccess adminEmail) {
        return adminService.fetchAllUsers(adminEmail);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return adminService.deleteUser(id);
    }

    @PutMapping("/restrict/{id}")
    public ResponseEntity<?> restrictUser(@PathVariable Long id) {
      return adminService.restrictUser(id);
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<?> activateUser(@PathVariable Long id) {
       return adminService.activateUser(id);
    }

}
