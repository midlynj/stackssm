package com.example.stacks.repository;

import com.example.stacks.entity.ERole;
import com.example.stacks.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    Set<User> findUsersByRolesName(ERole roleName);


}
