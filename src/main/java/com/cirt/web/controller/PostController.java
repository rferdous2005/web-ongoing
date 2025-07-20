package com.cirt.web.controller;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.cirt.web.entity.Post;
import com.cirt.web.service.PostService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
public class PostController {
    @Autowired
    private PostService postService;

    private final int PAGE_SIZE = 6;

    @GetMapping("/{category}")
    public String showPostListCategorywise(@PathVariable("category") String category, @RequestParam(defaultValue = "0") int page, Model model, HttpServletRequest request, HttpServletResponse response) {
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
        System.out.println(postListPaged.getTotalPages());
        switch (category) {
            case "logout":
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                if (auth != null) {
                    new SecurityContextLogoutHandler().logout(request, response, auth);
                }
                return "redirect:/login";
            case "alerts":
            case "advisories":
            case "bulletins":
            case "acts":
            case "notices":
            case "news":
            case "events":
                return "post/post-list-horizontal-single";
            case "articles":
            case "policies":
            case "magazines":
            case "guidelines":
            case "documents":
                return "post/post-list-default-card";
            default:
                return "404";
        }
        
    }

    @GetMapping("/{category}/{post-uri}")
    public String showSinglePost(@PathVariable("category") String category, @PathVariable("post-uri") String uri, Model model) {
        Post returnedPost = postService.findSinglePostForPublic(category, uri).orElse(null);
        Page<Post> postListPaged = postService.getPaginatedPostsForPublic(category, PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "publishedAt")));
        if(postListPaged.getTotalElements() == 0) {
            model.addAttribute("postList", new LinkedList<>());
        } else {
            model.addAttribute("suggestedPostList", postListPaged.getContent());
        }
        if(returnedPost == null) {
            return "404";
        }
        model.addAttribute("post", returnedPost);
        return "post/single-post";
    }
}
