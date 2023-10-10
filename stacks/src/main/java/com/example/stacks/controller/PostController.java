package com.example.stacks.controller;

import com.example.stacks.dto.PostDto;
import com.example.stacks.entity.Post;
import com.example.stacks.payload.UpdatePost;
import com.example.stacks.service.PostServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/posts", headers = "Accept=application/json")
public class PostController {
    private  final PostServiceImpl postService;

    @GetMapping("/all")
    public List<Post> fetchPosts() {
        return postService.fetchAllPosts();
    }

    @GetMapping("/{id}")
    public Optional<Post> fetchPostById(@PathVariable Long id) {
        return postService.fetchPostById(id);
    }

    @PostMapping("/{id}/new-post")
    public void createPost(@PathVariable Long id, @RequestBody PostDto postDto) {
        postService.createPost(id, postDto);
    }

    @PutMapping("{id}")
    public void updatePost(@PathVariable long id, @RequestBody UpdatePost updatePost) {
        postService.updatePost(id, updatePost);
    }
}
