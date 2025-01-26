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

import com.cirt.web.entity.Post;
import com.cirt.web.service.PostService;


@Controller
public class PostController {
    @Autowired
    private PostService postService;

    private final int PAGE_SIZE = 5;

    @GetMapping("/{category}")
    public String showPostListCategorywise(@PathVariable("category") String category, @RequestParam(defaultValue = "0") int page, Model model) {
        String categoryCap = category.substring(0, 1).toUpperCase() + category.substring(1);
        model.addAttribute("categoryCap", categoryCap);
        model.addAttribute("category", category);
        model.addAttribute("t", categoryCap +" | BGD e-GOV CIRT");

        Page<Post> postListPaged = postService.getPaginatedPostsForPublic(category, PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "publishedAt")));
        if(postListPaged.getTotalElements() == 0) {
            model.addAttribute("postList", new LinkedList<>());
        } else {
            model.addAttribute("postList", postListPaged.getContent());
        }
        model.addAttribute("page", page);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", postListPaged.getTotalPages());
        switch (category) {
            case "alerts":
            case "advisories":
            case "bulletins":
            case "acts":
            case "policies":
            case "notices":
            case "news":
            case "events":
            case "articles":
            case "magazines":
            case "guidelines":
            case "documents":
                return "post/post-list";
            default:
                return "404";
        }
        
    }

    @GetMapping("/{category}/{post-uri}")
    public String showSinglePost(@PathVariable("category") String category, @PathVariable("post-uri") String uri, Model model) {
        Post returnedPost = postService.findSinglePostForPublic(category, uri).orElse(null);
        if(returnedPost == null) {
            return "404";
        }
        model.addAttribute("post", returnedPost);
        return "post/single-post";
    }
}
