package com.example.stacks.interfaces;

import com.example.stacks.dto.PostDto;
import com.example.stacks.entity.Post;
import com.example.stacks.payload.UpdatePost;

import java.util.List;
import java.util.Optional;

public interface PostInterface {
    List<Post> fetchAllPosts();
    Optional<Post> fetchPostById(Long id);
    void createPost(Long id, PostDto postDto);
    void updatePost(Long id, UpdatePost updatePost);
}
