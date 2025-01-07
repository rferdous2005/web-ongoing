package com.cirt.web.controller;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cirt.web.dto.MediaDto;
import com.cirt.web.entity.Media;
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
    public String getMediapage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, Model model) {
        Page<Media> mediaListPaged = mediaService.getPaginatedMedias(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
        if(mediaListPaged.getTotalElements() == 0) {
            model.addAttribute("mediaList", new LinkedList<>());
        } else {
            model.addAttribute("mediaList", mediaListPaged.getContent());
        }
        model.addAttribute("media", new MediaDto());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", mediaListPaged.getTotalPages());
        return "admin/admin-media";
    }

    @PostMapping("/media")
    public String saveFileInfo(@ModelAttribute("media") MediaDto mediaDto) {
        mediaService.addMedia(mediaDto);
        return "redirect:/admin/media";
    }   

    
}
