package com.cirt.web.controller;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.cirt.web.dto.MediaDto;
import com.cirt.web.entity.Media;
import com.cirt.web.entity.Post;
import com.cirt.web.service.PostService;


@Controller
public class PostController {
    @Autowired
    private PostService postService;

    private final int PAGE_SIZE = 5;

    @GetMapping("/{category}")
    public String showPostListCategorywise(@PathVariable("category") String category, @RequestParam(defaultValue = "0") int page, Model model) {
        category = category.substring(0, 1).toUpperCase() + category.substring(1);
        model.addAttribute("category", category);
        model.addAttribute("t", category +" | BGD e-GOV CIRT");

        Page<Post> postListPaged = postService.getPaginatedPostsForPublic(category, PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "publishedAt")));
        if(postListPaged.getTotalElements() == 0) {
            model.addAttribute("postList", new LinkedList<>());
        } else {
            model.addAttribute("postList", postListPaged.getContent());
        }
        model.addAttribute("page", page);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", postListPaged.getTotalPages());
        return "post/post-list";
    }

    @GetMapping("/{category}/{post-uri}")
    public String showSinglePost(@PathVariable("category") String category, @PathVariable("post-uri") String uri) {
        return "post/single-post";
    }
}
