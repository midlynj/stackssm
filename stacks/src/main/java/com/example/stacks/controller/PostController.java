package com.example.stacks.controller;

import com.example.stacks.dto.PostDto;
import com.example.stacks.entity.Post;
import com.example.stacks.service.PostServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Post fetchPostById(@PathVariable Long id) {
        return postService.fetchPostById(id);
    }

    @PostMapping("/{id}/new-post")
    public void createPost(@PathVariable Long id, @RequestBody PostDto postDto) {
        postService.createPost(id, postDto);
    }

}
