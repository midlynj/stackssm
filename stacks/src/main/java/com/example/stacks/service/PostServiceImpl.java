package com.example.stacks.service;


import com.example.stacks.dto.PostDto;
import com.example.stacks.entity.Post;
import com.example.stacks.entity.User;
import com.example.stacks.interfaces.PostInterface;
import com.example.stacks.repository.PostRepository;
import com.example.stacks.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostInterface {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(PostServiceImpl.class);

    @Override
    public List<Post> fetchAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post fetchPostById(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            LOGGER.error("Post not found");
            throw new RuntimeException("No post with id " + id);
        }

        return optionalPost.get();
    }

    @Override
    public void createPost(Long id, PostDto postDto) {
        User author = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        if (postDto.getContent().isEmpty()) {
            LOGGER.error("Post Creation Failed: Post is empty");
            throw new RuntimeException("Post cannot be empty");
        }

        Post post = new Post();
        post.setContent(postDto.getContent());
        post.setAuthor(author);
        post.setCreatedAt(postDto.getCreatedAt());

        LOGGER.info("New post created");

        postRepository.save(post);
    }
}
