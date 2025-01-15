package com.cirt.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cirt.web.entity.Post;
import com.cirt.web.repository.PostRepository;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Page<Post> getPaginatedPostsForPublic(String category, Pageable pageable) {
        return this.postRepository.findByCategoryAndVisibility(category, "public", pageable);
    }

    public Post addPostByAdmin(Post post) {
        return this.postRepository.save(post);
    }
}
