package com.cirt.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cirt.web.dto.PostSummaryDto;
import com.cirt.web.entity.Homepage;
import com.cirt.web.entity.Post;
import com.cirt.web.repository.HomepageRepository;
import com.cirt.web.repository.PostRepository;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private HomepageRepository homepageRepository;

    public Page<Post> getPaginatedPostsForPublic(String category, Pageable pageable) {
        return this.postRepository.findByVisibilityAndCategory("public", category, pageable);
    }

    public Post addPostByAdmin(Post post) {
        return this.postRepository.save(post);
    }

    public Page<PostSummaryDto> getPaginatedPostsForAdmin(Pageable pageable)  {
        return this.postRepository.findBy(pageable);
    }

    public Optional<Post> findByIdForAdmin(int id) {
        return this.postRepository.findById(id);
    }

    public Optional<Post> findSinglePostForPublic(String category, String uri) {
        return this.postRepository.findByVisibilityAndCategoryAndUri("public", category, uri);
    }

    public Homepage getOnlyOneHomepageContent() {
        Homepage homepage = this.homepageRepository.findById(1).get();
        if(homepage == null) System.out.println("Null!!! No data found on Homepage Table");
        return homepage;
    }

    public Homepage updateOnlyOneHomepageContent(Homepage newContent) {
        Homepage homepage = this.homepageRepository.findById(1).get();
        if(homepage == null) System.out.println("Null!!! No data found on Homepage Table");
        homepage.setWarningColor(newContent.getWarningColor());
        homepage.setWarningLabel(newContent.getWarningLabel());
        homepage.setHighlights(newContent.getHighlights());

        Homepage newHomepage = this.homepageRepository.save(homepage);
        return newHomepage;
    }

    public void update(int id, Post newP) {
        Post returnedPost = this.findByIdForAdmin(id).orElse(null);
        returnedPost.setBody(newP.getBody());
        returnedPost.setCategory(newP.getCategory());
        returnedPost.setPublishedAt(newP.getPublishedAt());
        returnedPost.setSubTitle(newP.getSubTitle());
        returnedPost.setThumbnail(newP.getThumbnail());
        returnedPost.setTitle(newP.getTitle());
        returnedPost.setUri(newP.getUri());
        returnedPost.setVisibility(newP.getVisibility());
        this.postRepository.save(returnedPost);
    }
}
