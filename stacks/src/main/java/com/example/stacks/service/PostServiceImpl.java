package com.example.stacks.service;


import com.example.stacks.dto.PostDto;
import com.example.stacks.entity.Post;
import com.example.stacks.entity.User;
import com.example.stacks.interfaces.PostInterface;
import com.example.stacks.misc.FieldHelper;
import com.example.stacks.payload.UpdatePost;
import com.example.stacks.repository.PostRepository;
import com.example.stacks.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostInterface {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public List<Post> fetchAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Optional<Post> fetchPostById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public void createPost(Long id, PostDto postDto) {
        User author = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        Post post = new Post();
        post.setContent(postDto.getContent());
        post.setAuthor(author);
        post.setCreatedAt(postDto.getCreatedAt());

        postRepository.save(post);
    }

    @Override
    public void updatePost(Long id, UpdatePost updatePost) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty())
            throw new RuntimeException( "Post " + id + " not found");

        Post originalPost = optionalPost.get();

        updatePost.setId(id);
        BeanUtils.copyProperties(updatePost, originalPost, FieldHelper.getNullPropertyNames(updatePost));

        postRepository.save(originalPost);
    }
}
