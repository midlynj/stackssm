package com.example.stacks;

import com.example.stacks.controller.AdminController;
import com.example.stacks.payload.AdminAccess;
import com.example.stacks.service.AdminServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {
    @InjectMocks
    private AdminController adminController;
    @Mock
    private AdminServiceImpl adminService;

    @Test
    void testFindAllUsers() {
        AdminAccess adminEmail = new AdminAccess("a@email.com");
        when(adminService.fetchAllUsers(adminEmail)).thenReturn(ResponseEntity.ok().build());

        ResponseEntity<?> response = adminController.fetchAllUsers(adminEmail);
        assertEquals(HttpStatus.OK ,response.getStatusCode());
    }

    @Test
    void testRestrictUser() {
        adminController.restrictUser(1L);

        verify(adminService, times(1)).restrictUser(1L);
    }

    @Test
    void testActivateUser() {
        adminController.activateUser(1L);

        verify(adminService, times(1)).activateUser(1L);
    }

    @Test
    void testDeleteUser() {
        adminController.deleteUser(1L);

        verify(adminService, times(1)).deleteUser(1L);
    }
}
