package com.cirt.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class PostController {
    
    @GetMapping("/{category}")
    public String showPostListCategorywise(@PathVariable("category") String category, Model model) {
        category = category.substring(0, 1).toUpperCase() + category.substring(1);
        model.addAttribute("category", category);
        model.addAttribute("t", category +" | BGD e-GOV CIRT");
        return "post/post-list";
    }

    @GetMapping("/{category}/{post-uri}")
    public void showSinglePost(@PathVariable("post-uri") String uri) {

    }
}
