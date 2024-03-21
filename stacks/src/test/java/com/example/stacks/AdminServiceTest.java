package com.example.stacks;

import com.example.stacks.entity.ERole;
import com.example.stacks.entity.Role;
import com.example.stacks.entity.Status;
import com.example.stacks.entity.User;
import com.example.stacks.payload.AdminAccess;
import com.example.stacks.repository.RoleRepository;
import com.example.stacks.repository.UserRepository;
import com.example.stacks.service.AdminServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {
    @InjectMocks
    AdminServiceImpl service;
    @Mock
    UserRepository dao;
    @Mock
    RoleRepository roleRepository;
     Set<Role> adminRole = new HashSet<>();
     Set<Role> userRole = new HashSet<>();
     Set<User> users = new HashSet<>();

    private User adminUser;
    private User user1;
    private User inactiveUser;

    @BeforeEach
    void setUp() {
        Role admin = new Role(1L, ERole.ADMIN);
        Role user = new Role(2L, ERole.USER);
        adminRole.add(admin);
        userRole.add(user);

        adminUser = new User(1L,"a","b","a@email.com","123",null,null,null,Status.ACTIVE,adminRole);

        user1 = new User(2L, "c", "d", "cd@email.com", "123", null, null, null, Status.ACTIVE, userRole);
        users.add(user1);

        inactiveUser = new User(3L, "e", "f", "ef@email.com", "123", null, null, null, Status.INACTIVE, userRole);
        users.add(user1);
    }

    @Test
    void testFindAllUsers() {
        when(dao.findUserByEmail("a@email.com")).thenReturn(Optional.ofNullable(adminUser));
        when(roleRepository.findByName(ERole.ADMIN)).thenReturn(Optional.of(new Role(1L, ERole.ADMIN)));
        when(dao.findUsersByRolesName(ERole.USER)).thenReturn(users);

        AdminAccess adminEmail = new AdminAccess("a@email.com");

        ResponseEntity<?> response = service.fetchAllUsers(adminEmail);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteUserById() {
       when(dao.findById(2L)).thenReturn(Optional.ofNullable(user1));
        service.deleteUser(2L);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(dao, times(1)).save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(Status.INACTIVE, capturedUser.getStatus());
        assertEquals("c", capturedUser.getFirstName());

        Set<Role> roles = capturedUser.getRoles();

        Set<ERole> roleNames = roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        assertTrue(roleNames.contains(ERole.USER));
        assertFalse(roleNames.contains(ERole.ADMIN));

    }

    @Test
    void testRestrictUserById() {
        when(dao.findById(2L)).thenReturn(Optional.ofNullable(user1));
        service.restrictUser(2L);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(dao, times(1)).save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(Status.RESTRICTED, capturedUser.getStatus());
        assertEquals("c", capturedUser.getFirstName());

        Set<Role> roles = capturedUser.getRoles();

        Set<ERole> roleNames = roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        assertTrue(roleNames.contains(ERole.USER));
        assertFalse(roleNames.contains(ERole.ADMIN));

    }

    @Test
    void testActivateUserById() {
        when(dao.findById(2L)).thenReturn(Optional.ofNullable(inactiveUser));
        service.activateUser(2L);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(dao, times(1)).save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(Status.ACTIVE, capturedUser.getStatus());
        assertEquals("e", capturedUser.getFirstName());

        Set<Role> roles = capturedUser.getRoles();

        Set<ERole> roleNames = roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        assertTrue(roleNames.contains(ERole.USER));
        assertFalse(roleNames.contains(ERole.ADMIN));

    }
}
