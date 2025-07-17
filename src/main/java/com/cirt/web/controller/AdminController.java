package com.cirt.web.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.cirt.web.dto.PasswordResetDto;
import com.cirt.web.dto.PostSummaryDto;
import com.cirt.web.entity.Homepage;
import com.cirt.web.entity.Incident;
import com.cirt.web.entity.Media;
import com.cirt.web.entity.Post;
import com.cirt.web.entity.User;
import com.cirt.web.service.IncidentService;
import com.cirt.web.service.MediaService;
import com.cirt.web.service.PostService;
import com.cirt.web.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private MediaService mediaService; 

    @Autowired
    private PostService postService;

    @Autowired
    private IncidentService incidentService;

    @GetMapping("/home")
    public String getHomepage(Model model) {
        Homepage homepageContent = this.postService.getOnlyOneHomepageContent();
        model.addAttribute("content", homepageContent);
        return "admin/admin-home";
    }

    @PostMapping("/home")
    public String getHomepage(@ModelAttribute("content") Homepage homepageContent, Model model, RedirectAttributes redirectAttributes) {
        String colorCode = homepageContent.getWarningLabel().split("\\|")[1];
        homepageContent.setWarningColor(colorCode);
        Homepage homepage = this.postService.updateOnlyOneHomepageContent(homepageContent);
        if(homepage != null) {
            redirectAttributes.addAttribute("type", "success");
            redirectAttributes.addAttribute("message", "Homepage content update successfull!!");
        }
        return "redirect:/admin/home";
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
    public String updateSinglePost(@PathVariable int id, @ModelAttribute("post") Post post, Model model) {
        this.postService.update(id, post);
        return "redirect:/admin/posts";
    }

    @GetMapping("/post")
    public String postAddForm(Model model) {
        Post placeholderPost = new Post();
        model.addAttribute("post", placeholderPost);
        return "admin/admin-post-add-form";
    }

    @PostMapping("/post")
    public String postSave(Model model, @ModelAttribute("post") Post post, final RedirectAttributes redirectAttributes) {
        post.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        Post returnedPost = this.postService.addPostByAdmin(post);
        return "redirect:/admin/posts";
    }

    @GetMapping("/user")
    public String getUsersPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authorities: " + auth.getAuthorities()+" "+ auth.getName());
        Page<User> userListPaged = userService.getUsersPaginated(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
        if(userListPaged.getTotalElements() == 0) {
            model.addAttribute("userList", new LinkedList<>());
        } else {
            model.addAttribute("userList", userListPaged.getContent());
        }
        model.addAttribute("page", page);
        model.addAttribute("user", new User());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", userListPaged.getTotalPages());
        return "admin/admin-user-list";
    }

    @PostMapping("/user")
    public String userSave(Model model, @ModelAttribute("user") User user, final RedirectAttributes redirectAttributes) {
        this.userService.addUserByAdmin(user);
        return "redirect:/admin/user";
    }

    @GetMapping("/user-delete")
    public String userDelete(@RequestParam int id, @RequestParam(defaultValue = "0") int page, Model model) {
        this.userService.deleteUser(id);
        return "redirect:/admin/user?page="+ page;
    }

    @GetMapping("/my-password")
    public String passwordResetPage(@ModelAttribute("passwordReset") PasswordResetDto passwordResetDto, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PasswordResetDto passwordDto = new PasswordResetDto();
        passwordDto.setUsername(auth.getName());
        model.addAttribute("passwordDto", passwordDto);
        return "admin/admin-my-password";
    }
    @PostMapping("/my-password")
    public String passwordReset(@ModelAttribute("passwordDto") PasswordResetDto passwordResetDto, Model model) {
        boolean match = this.userService.changePassword(passwordResetDto);
        if (match) {
            model.addAttribute("match", true);
            return "admin/admin-my-password";
        } else {
            model.addAttribute("match", false);
            return "admin/admin-my-password";
        }
    }

    @GetMapping("/incident")
    public String showAllIncidents(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "15") int size, Model model) {
        Page<Incident> incidentListPaged = incidentService.getPaginatedIncidents(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
        if(incidentListPaged.getTotalElements() == 0) {
            model.addAttribute("incidentList", new LinkedList<>());
        } else {
            model.addAttribute("incidentList", incidentListPaged.getContent());
        }
        model.addAttribute("page", page);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", incidentListPaged.getTotalPages());
        return "admin/admin-incident-list";
    }

    @GetMapping("/incident/{id}")
    public String showIncidentDetails(@PathVariable int id, Model model) {
        Incident incident = incidentService.getSingleIncident(id);
        model.addAttribute("incident", incident);
        return "admin/admin-incident-details";
    }

    @PostMapping("/incident/{id}")
    public String respondToIncident(@PathVariable int id, Model model) {
        incidentService.makeResponseDone(id);
        return "redirect:/admin/incident";
    }
}
