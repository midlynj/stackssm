package com.example.stacks.repository;

import com.example.stacks.entity.ERole;
import com.example.stacks.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
