package com.cirt.web.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cirt.web.dto.PostSummaryDto;
import com.cirt.web.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    
    Page<Post> findByVisibilityAndCategory(String category, String visibility, Pageable pageable);

    Page<PostSummaryDto> findBy(Pageable pageable);

    Optional<Post> findByVisibilityAndCategoryAndUri(String visibility, String category, String uri);
}