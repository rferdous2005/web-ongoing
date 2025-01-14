package com.cirt.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cirt.web.entity.AdminPost;
import com.cirt.web.repository.AdminPostRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AdminPostService {

    @Autowired
    private AdminPostRepository adminPostRepository;

    private final String uploadDir = "uploads/";

    public void savePost(String title, String content, String page, MultipartFile file) throws IOException {
        AdminPost post = new AdminPost();
        post.setTitle(title);
        post.setContent(content);
        post.setPage(page);

        // Handle file upload
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            post.setFileName(fileName);

            // Save file to local directory
            Path filePath = Paths.get(uploadDir + fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());
        }

        adminPostRepository.save(post);
    }
}
