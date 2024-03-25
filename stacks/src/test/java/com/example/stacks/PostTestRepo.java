package com.example.stacks;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.stacks.entity.Post;

public interface PostTestRepo extends JpaRepository<Post, Long> {
    
}