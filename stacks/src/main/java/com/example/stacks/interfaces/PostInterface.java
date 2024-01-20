package com.example.stacks.interfaces;

import com.example.stacks.dto.PostDto;
import com.example.stacks.entity.Post;

import java.util.List;

public interface PostInterface {
    List<Post> fetchAllPosts();
    Post fetchPostById(Long id);
    void createPost(Long id, PostDto postDto);
}
