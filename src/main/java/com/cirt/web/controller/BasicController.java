package com.cirt.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.http.HttpHeaders;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cirt.web.entity.Media;
import com.cirt.web.entity.Post;
import com.cirt.web.repository.MediaRepository;
import com.cirt.web.service.PostService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
@RequestMapping
public class BasicController {
    @Value("${app.file.location}")
    private String UPLOAD_DIR;
    @Autowired
    MediaRepository mediaRepository;
    @Autowired
    PostService postService;

    private final int PAGE_SIZE = 5;

    @GetMapping
    public String getMethodName(Model model) {

        Page<Post> alertsList = postService.getPaginatedPostsForPublic("alerts", PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "publishedAt")));
        Page<Post> magazinesList = postService.getPaginatedPostsForPublic("magazines", PageRequest.of(0, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "publishedAt")));
        Page<Post> newsList = postService.getPaginatedPostsForPublic("news", PageRequest.of(0, 6, Sort.by(Sort.Direction.DESC, "publishedAt")));
        // if(alertsList.getTotalElements() == 0) {
        //     model.addAttribute("postList", new LinkedList<>());
        // } else {
        //     model.addAttribute("postList", postListPaged.getContent());
        // }
        model.addAttribute("alertsList", alertsList);
        model.addAttribute("magazinesList", magazinesList);
        model.addAttribute("newsList", newsList);
        // latest news or events
        return "home";
    }
    
    @GetMapping("/who-we-are")
    public String getIntroWho() {
        return "basic/who";
    }

    @GetMapping("/what-we-do")
    public String getIntroWhat() {
        return "basic/what";
    }

    @GetMapping("/mission-vision")
    public String getIntroMisVis() {
        return "basic/mis-vis";
    }
    
    @GetMapping("/media")
    public void viewFile(HttpServletResponse response, HttpServletRequest request, @RequestParam("id") String fileName) throws Exception {
        File file = new File( UPLOAD_DIR + fileName);
        FileInputStream inStream = new FileInputStream(file);
        // gets MIME type of the file
        String mimeType = "application/octet-stream";
        OutputStream outStream = response.getOutputStream();


        // modifies response
        response.setContentType(mimeType);
        response.setContentLength((int) file.length());

        // forces download
        String headerKey = "Content-Disposition";
        String headerValue = String.format("inline; filename=\"%s\"", fileName);
        response.setHeader(headerKey, headerValue);

        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inStream.close();
        outStream.close();
    }

    @GetMapping("/login")
    public String loginPage(Model model, HttpServletRequest request) {
        return "basic/login-form";
    }

    @GetMapping("/service")
    public String serviceDescription(Model model, HttpServletRequest request) {
        return "basic/services";
    }

    @GetMapping("/report-incident")
    public String reportIncidentForm(Model model, HttpServletRequest request) {
        return "fragments/report-incident";
    }
}
