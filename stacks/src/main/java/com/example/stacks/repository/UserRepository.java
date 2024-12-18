package com.example.stacks.repository;

import com.example.stacks.entity.ERole;
import com.example.stacks.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    Set<User> findUsersByRolesName(ERole roleName);

    @Query("SELECT u FROM User u WHERE u.lastName = :lastName")
    User findByLast(@Param("lastName") String lastName);

    default User findByLastNameCache(String lastName) {
        return findByLast(lastName);
    }

}
