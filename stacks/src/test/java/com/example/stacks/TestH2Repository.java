package com.example.stacks;

import com.example.stacks.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestH2Repository extends JpaRepository<User,Long> {
}
