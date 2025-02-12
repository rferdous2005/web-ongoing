package com.cirt.web.controller;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cirt.web.dto.MediaDto;
import com.cirt.web.dto.PostSummaryDto;
import com.cirt.web.entity.Media;
import com.cirt.web.entity.Post;
import com.cirt.web.service.MediaService;
import com.cirt.web.service.PostService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private MediaService mediaService; 

    @Autowired
    private PostService postService;

    @GetMapping("/home")
    public String getHomepage() {
        return "admin/admin-home";
    }
    
    @GetMapping("/media")
    public String getMediapage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, Model model) {
        Page<Media> mediaListPaged = mediaService.getPaginatedMedias(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
        if(mediaListPaged.getTotalElements() == 0) {
            model.addAttribute("mediaList", new LinkedList<>());
        } else {
            model.addAttribute("mediaList", mediaListPaged.getContent());
        }
        model.addAttribute("page", page);
        model.addAttribute("media", new MediaDto());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", mediaListPaged.getTotalPages());
        return "admin/admin-media";
    }

    @GetMapping("/media-delete")
    public String mediaDelete(@RequestParam int fileId, @RequestParam(defaultValue = "0") int page, Model model) {
        this.mediaService.deleteMedia(fileId);
        return "redirect:/admin/media?page="+ page;
    }
    @PostMapping("/media")
    public String saveFileInfo(@ModelAttribute("media") MediaDto mediaDto) {
        mediaService.addMedia(mediaDto);
        return "redirect:/admin/media";
    }   

    @GetMapping("/posts")
    public String getAllPostsPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "15") int size, Model model) {
        Page<PostSummaryDto> postListPaged = postService.getPaginatedPostsForAdmin(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
        if(postListPaged.getTotalElements() == 0) {
            model.addAttribute("postList", new LinkedList<>());
        } else {
            model.addAttribute("postList", postListPaged.getContent());
        }
        model.addAttribute("page", page);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", postListPaged.getTotalPages());
        return "admin/admin-post-list";
    }

    @GetMapping("/posts/{id}")
    public String getSinglePostPage(@PathVariable int id, Model model) {
        Post returnedPostData = postService.findByIdForAdmin(id).orElse(null);
        String[] publishedAtTokens = returnedPostData.getPublishedAt().toString().split(":");
        String publishedAt = String.join(":", publishedAtTokens[0], publishedAtTokens[1]);
        System.out.println(publishedAt);
        model.addAttribute("publishedAtStringified", publishedAt);
        model.addAttribute("post", returnedPostData);
        return "admin/admin-post-edit-form";
    }

    @PostMapping("/posts/{id}")
    public String updateSinglePost(@PathVariable int id, Model model) {
        Post returnedPostData = postService.findByIdForAdmin(id).orElse(null);
        String[] publishedAtTokens = returnedPostData.getPublishedAt().toString().split(":");
        String publishedAt = String.join(":", publishedAtTokens[0], publishedAtTokens[1]);
        System.out.println(publishedAt);
        model.addAttribute("publishedAtStringified", publishedAt);
        model.addAttribute("post", returnedPostData);
        return "admin/admin-post-edit-form";
    }

    @GetMapping("/post")
    public String postAddForm(Model model) {
        Post placeholderPost = new Post();
        model.addAttribute("post", placeholderPost);
        return "admin/admin-post-add-form";
    }

    @PostMapping("/post")
    public String postSave(Model model, @ModelAttribute("post") Post post, final RedirectAttributes redirectAttributes) {
        post.setCreatedBy("Admin");
        Post returnedPost = this.postService.addPostByAdmin(post);
        return "redirect:/admin/posts";
    }

    @GetMapping("/user")
    public String getUsersPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, Model model) {
        Page<Media> mediaListPaged = mediaService.getPaginatedMedias(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
        if(mediaListPaged.getTotalElements() == 0) {
            model.addAttribute("mediaList", new LinkedList<>());
        } else {
            model.addAttribute("mediaList", mediaListPaged.getContent());
        }
        model.addAttribute("page", page);
        model.addAttribute("user", new MediaDto());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", mediaListPaged.getTotalPages());
        return "admin/admin-user-list";
    }
}
