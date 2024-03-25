package com.example.stacks;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.stacks.entity.ERole;
import com.example.stacks.entity.User;

public interface AdminTestRepo extends JpaRepository<User, Long> {
     Optional<User> findUserByEmail(String email);

    Set<User> findUsersByRolesName(ERole roleName);
    
}
