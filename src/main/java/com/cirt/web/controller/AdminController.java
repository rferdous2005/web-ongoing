package com.cirt.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cirt.web.dto.MediaDto;
import com.cirt.web.service.MediaService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private MediaService mediaService; 

    @GetMapping("/home")
    public String getHomepage() {
        return "admin/admin-home";
    }
    
    @GetMapping("/media")
    public String getMediapage(Model model) {
        model.addAttribute("media", new MediaDto());
        return "admin/admin-media";
    }

    @PostMapping("/media")
    public String saveFileInfo(@ModelAttribute("media") MediaDto mediaDto) {
        mediaService.addMedia(mediaDto);
        System.out.println(mediaDto.getFileExtension());
        return "redirect:/admin/media";
    }
}
